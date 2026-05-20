# run.ps1
# Helper script to compile, test, or run the Spring Boot project using the local JDK and Maven.

param (
    [switch]$Test,
    [switch]$Run,
    [switch]$Build
)

$toolsDir = "C:\Users\Welcome\java-tools"

# Find JDK and Maven paths dynamically
$jdkDirs = Get-ChildItem -Path $toolsDir -Directory -Filter "jdk-21*"
$mavenDirs = Get-ChildItem -Path $toolsDir -Directory -Filter "apache-maven-*"

if ($jdkDirs.Count -eq 0 -or $mavenDirs.Count -eq 0) {
    Write-Error "Local JDK 21 or Apache Maven was not found in $toolsDir. Please run the setup-tools script first."
    exit 1
}

$jdkHome = $jdkDirs[0].FullName
$mavenHome = $mavenDirs[0].FullName

Write-Host "Configuring local environment variables..."
$env:JAVA_HOME = $jdkHome
$env:M2_HOME = $mavenHome
$env:PATH = "$jdkHome\bin;$mavenHome\bin;$env:PATH"

Write-Host "Using Java version:"
java -version

Write-Host "Using Maven version:"
mvn -version

if ($Test) {
    Write-Host "Running JPA mapping and integration tests..."
    mvn clean test
} elseif ($Run) {
    Write-Host "Starting Spring Boot application locally..."
    $postgresActive = $false
    if (Get-NetTCPConnection -LocalPort 5432 -ErrorAction SilentlyContinue) {
        $postgresActive = $true
    }
    
    if ($postgresActive) {
        Write-Host "PostgreSQL is active on port 5432. Starting application..."
        mvn spring-boot:run
    } else {
        Write-Error "PostgreSQL is NOT running on port 5432!"
        Write-Host "To resolve this, please start the PostgreSQL database container by running:"
        Write-Host "  docker start pg-user-management"
        Write-Host "Or if you haven't created the container yet, run:"
        Write-Host "  docker run --name pg-user-management -e POSTGRES_DB=user_management_db -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres"
        exit 1
    }
} elseif ($Build) {
    Write-Host "Building project package..."
    mvn clean package -DskipTests
} else {
    Write-Host "==========================================================="
    Write-Host "Usage helper for run.ps1:"
    Write-Host "  .\run.ps1 -Test    : Runs all unit and JPA integration tests"
    Write-Host "  .\run.ps1 -Run     : Starts the Spring Boot application locally"
    Write-Host "  .\run.ps1 -Build   : Compiles and packages the application"
    Write-Host "==========================================================="
}
