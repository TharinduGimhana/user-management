package com.example.usermanagement;

import com.example.usermanagement.entity.*;
import com.example.usermanagement.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserManagementApplicationTests {

    @Autowired
    private Db1UserRepository userRepository;

    @Autowired
    private Db1VirtualContactRepository virtualContactRepository;

    @Autowired
    private Db1UserContactRepository userContactRepository;

    @Autowired
    private SysSubjectRepository subjectRepository;

    @Autowired
    private Db1UserSubjectRepository userSubjectRepository;

    @Autowired
    private SysPrincipalRepository principalRepository;

    @Autowired
    private SysUserActivityRepository userActivityRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void testJpaEntitiesAndRelationships() {
        // 1. Create and Save SysPrincipal
        SysPrincipal principal = new SysPrincipal();
        principal.setSubjectId(101L);
        principal = principalRepository.save(principal);
        assertNotNull(principal.getId());

        // 2. Create and Save SysSubject linked to principal
        SysSubject subject = new SysSubject();
        subject.setSubjectCode("Computer Science");
        subject.setFirstName("John");
        subject.setLastName("Doe");
        subject.setVerificationStatus("VERIFIED");
        subject.setDisplayName("CS-John");
        subject.setOrganization("Acme Corp");
        subject.setPrincipal(principal);
        subject = subjectRepository.save(subject);
        assertNotNull(subject.getId());

        // 3. Create and Save Db1User
        Db1User user = new Db1User();
        user.setEmail("user@example.com");
        user.setUsername("johndoe");
        user.setMobile("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailVerified(true);
        user.setPrincipalId("principal-999");
        user.setResidence("New York, USA");
        user.setVerificationStatus("SMS_VERIFIED");
        user = userRepository.save(user);
        assertNotNull(user.getId());

        // 4. Create and Save SysUserActivity linked to user
        SysUserActivity activity = new SysUserActivity();
        activity.setUser(user);
        activity.setActivityType("LOGIN");
        activity.setActivityStatus("Logged in from IP 127.0.0.1");
        activity.setDescription("User login activity");
        activity = userActivityRepository.save(activity);
        assertNotNull(activity.getId());

        // 5. Create and Save Db1VirtualContact
        Db1VirtualContact contact = new Db1VirtualContact();
        contact.setRelationshipType("FRIEND");
        contact.setPrivateContact("Secret Phone Number");
        contact.setEmail("friend@example.com");
        contact.setFirstName("Alice");
        contact.setMiddleName("M");
        contact.setLastName("Smith");
        contact.setBirthDate(LocalDateTime.of(1995, 8, 24, 0, 0));
        contact.setNickName("Ally");
        contact = virtualContactRepository.save(contact);
        assertNotNull(contact.getId());

        // 6. Create and Save Db1UserContact linking User and VirtualContact
        Db1UserContact userContact = new Db1UserContact();
        userContact.setUser(user);
        userContact.setContact(contact);
        userContact = userContactRepository.save(userContact);
        assertNotNull(userContact.getId());

        // 7. Create and Save Db1UserSubject linking User and Subject
        Db1UserSubject userSubject = new Db1UserSubject();
        userSubject.setUser(user);
        userSubject.setSubject(subject);
        userSubject = userSubjectRepository.save(userSubject);
        assertNotNull(userSubject.getId());

        // Flush and clear persistence context to force reloading from DB
        userRepository.flush();
        entityManager.clear();

        // Retrieve and Verify Assertions
        Optional<Db1User> foundUserOpt = userRepository.findById(user.getId());
        assertTrue(foundUserOpt.isPresent());
        Db1User foundUser = foundUserOpt.get();

        assertEquals("user@example.com", foundUser.getEmail());
        assertEquals(1, foundUser.getActivities().size());
        assertEquals("LOGIN", foundUser.getActivities().get(0).getActivityType());

        assertEquals(1, foundUser.getUserContacts().size());
        assertEquals("FRIEND", foundUser.getUserContacts().get(0).getContact().getRelationshipType());

        assertEquals(1, foundUser.getUserSubjects().size());
        assertEquals("Computer Science", foundUser.getUserSubjects().get(0).getSubject().getSubjectCode());
        assertEquals(principal.getId(), foundUser.getUserSubjects().get(0).getSubject().getPrincipal().getId());
    }
}
