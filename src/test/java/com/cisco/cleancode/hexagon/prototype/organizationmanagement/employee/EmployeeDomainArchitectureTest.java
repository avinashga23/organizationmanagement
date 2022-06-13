package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** The type Employee domain architecture test. */
class EmployeeDomainArchitectureTest {

  /** The constant EMPLOYEE. */
  public static final String EMPLOYEE =
      "com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee";

  /** The constant ADAPTER_IN_WEB. */
  public static final String ADAPTER_IN_WEB = EMPLOYEE + ".adapter.in.web";

  /** The constant ADAPTER_OUT_PERSISTENCE. */
  public static final String ADAPTER_OUT_PERSISTENCE = EMPLOYEE + ".adapter.out.persistence";

  /** The constant APPLICATION_PORT_IN. */
  public static final String APPLICATION_PORT_IN = EMPLOYEE + ".application.port.in";

  /** The constant APPLICATION_PORT_OUT. */
  public static final String APPLICATION_PORT_OUT = EMPLOYEE + ".application.port.out";

  /** The constant APPLICATION_SERVICE. */
  public static final String APPLICATION_SERVICE = EMPLOYEE + ".application.service";

  /** The constant DOMAIN. */
  public static final String DOMAIN = EMPLOYEE + ".domain";

  /** The constant importedClasses. */
  private static JavaClasses importedClasses;

  /** Sets up. */
  @BeforeAll
  static void setUp() {
    importedClasses = new ClassFileImporter().importPackages(EMPLOYEE);
  }

  /** Test incoming web adapter no dependants on persistence. */
  @Test
  void testIncomingWebAdapterNoDependantsOn_Persistence() {
    noClasses()
        .that()
        .resideInAPackage(ADAPTER_IN_WEB)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ADAPTER_OUT_PERSISTENCE)
        .check(importedClasses);
  }

  /** Test incoming web adapter no dependants on service. */
  @Test
  void testIncomingWebAdapterNoDependantsOn_Service() {
    noClasses()
        .that()
        .resideInAPackage(ADAPTER_IN_WEB)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(APPLICATION_SERVICE)
        .check(importedClasses);
  }

  /** Test out going persistence adapter no dependants on web adapter. */
  @Test
  void testOutGoingPersistenceAdapterNoDependantsOn_WebAdapter() {
    noClasses()
        .that()
        .resideInAPackage(ADAPTER_OUT_PERSISTENCE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(ADAPTER_IN_WEB)
        .check(importedClasses);
  }

  /** Test out going persistence adapter no dependants on service. */
  @Test
  void testOutGoingPersistenceAdapterNoDependantsOn_Service() {
    noClasses()
        .that()
        .resideInAPackage(ADAPTER_OUT_PERSISTENCE)
        .should()
        .dependOnClassesThat()
        .resideInAPackage(APPLICATION_SERVICE)
        .check(importedClasses);
  }

  /** Test incoming port no dependants outside web service itself. */
  @Test
  void testIncomingPortNoDependantsOutside_Web_Service_Itself() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_PORT_IN)
        .should()
        .onlyHaveDependentClassesThat()
        .resideInAnyPackage(ADAPTER_IN_WEB, APPLICATION_SERVICE, APPLICATION_PORT_IN)
        .check(importedClasses);
  }

  /** Test out going port no dependants outside persistence service itself. */
  @Test
  void testOutGoingPortNoDependantsOutside_Persistence_Service_Itself() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_PORT_OUT)
        .should()
        .onlyHaveDependentClassesThat()
        .resideInAnyPackage(ADAPTER_OUT_PERSISTENCE, APPLICATION_SERVICE)
        .check(importedClasses);
  }

  /** Test application service no dependents outside itself. */
  @Test
  void testApplicationServiceNoDependentsOutside_Itself() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_SERVICE)
        .should()
        .onlyHaveDependentClassesThat()
        .resideInAPackage(APPLICATION_SERVICE)
        .check(importedClasses);
  }

  /** Test service no dependee outside itself domain incoming port out going port. */
  @Test
  void testServiceNoDependeeOutside_Itself_Domain_IncomingPort_OutGoingPort() {
    noClasses()
        .that()
        .resideInAPackage(APPLICATION_SERVICE)
        .should()
        .accessClassesThat()
        .resideInAnyPackage(ADAPTER_IN_WEB, ADAPTER_OUT_PERSISTENCE)
        .check(importedClasses);
  }

  /** Test incoming web adapter have all package private classes. */
  @Test
  void testIncomingWebAdapterHaveAllPackagePrivateClasses() {
    classes()
        .that()
        .resideInAPackage(ADAPTER_IN_WEB)
        .should()
        .bePackagePrivate()
        .check(importedClasses);
  }

  /** Test out going persistence adapter have all package private classes. */
  @Test
  void testOutGoingPersistenceAdapterHaveAllPackagePrivateClasses() {
    classes()
        .that()
        .resideInAPackage(ADAPTER_OUT_PERSISTENCE)
        .should()
        .bePackagePrivate()
        .check(importedClasses);
  }

  /** Test application service have all package private classes. */
  @Test
  void testApplicationServiceHaveAllPackagePrivateClasses() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_SERVICE)
        .should()
        .bePackagePrivate()
        .check(importedClasses);
  }

  /** Test incoming port have all public classes. */
  @Test
  void testIncomingPortHaveAllPublicClasses() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_PORT_IN)
        .should()
        .bePublic()
        .check(importedClasses);
  }

  /** Test out going port have all public classes. */
  @Test
  void testOutGoingPortHaveAllPublicClasses() {
    classes()
        .that()
        .resideInAPackage(APPLICATION_PORT_OUT)
        .should()
        .bePublic()
        .check(importedClasses);
  }

  /** Test domain have all public classes. */
  @Test
  void testDomainHaveAllPublicClasses() {
    classes()
        .that()
        .resideInAPackage(DOMAIN)
        .should()
        .bePublic()
        .check(importedClasses);
  }
}
