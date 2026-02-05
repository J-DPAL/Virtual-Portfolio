package com.portfolio.skills.dataAccessLayer.entity;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class SkillEntityTest {

  private Skill skill;

  @BeforeEach
  void setUp() {
    skill =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testNoArgsConstructor() {
    Skill newSkill = new Skill();
    assertNull(newSkill.getId());
    assertNull(newSkill.getNameEn());
    assertNull(newSkill.getNameFr());
    assertNull(newSkill.getNameEs());
    assertNull(newSkill.getDescriptionEn());
    assertNull(newSkill.getDescriptionFr());
    assertNull(newSkill.getDescriptionEs());
    assertNull(newSkill.getProficiencyLevel());
    assertNull(newSkill.getCategory());
    assertNull(newSkill.getYearsOfExperience());
    assertNull(newSkill.getCreatedAt());
    assertNull(newSkill.getUpdatedAt());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Skill testSkill =
        new Skill(
            1L,
            "Python",
            "Python",
            "Python",
            "Python programming language",
            "Langage de programmation Python",
            "Lenguaje de programación Python",
            "Advanced",
            "Backend",
            8,
            now,
            now);

    assertEquals(1L, testSkill.getId());
    assertEquals("Python", testSkill.getNameEn());
    assertEquals("Python", testSkill.getNameFr());
    assertEquals("Python", testSkill.getNameEs());
    assertEquals("Python programming language", testSkill.getDescriptionEn());
    assertEquals("Langage de programmation Python", testSkill.getDescriptionFr());
    assertEquals("Lenguaje de programación Python", testSkill.getDescriptionEs());
    assertEquals("Advanced", testSkill.getProficiencyLevel());
    assertEquals("Backend", testSkill.getCategory());
    assertEquals(8, testSkill.getYearsOfExperience());
    assertEquals(now, testSkill.getCreatedAt());
    assertEquals(now, testSkill.getUpdatedAt());
  }

  @Test
  void testBuilder() {
    Skill builtSkill =
        Skill.builder()
            .id(2L)
            .nameEn("JavaScript")
            .nameFr("JavaScript")
            .nameEs("JavaScript")
            .descriptionEn("JavaScript programming language")
            .descriptionFr("Langage de programmation JavaScript")
            .descriptionEs("Lenguaje de programación JavaScript")
            .proficiencyLevel("Expert")
            .category("Frontend")
            .yearsOfExperience(12)
            .build();

    assertEquals(2L, builtSkill.getId());
    assertEquals("JavaScript", builtSkill.getNameEn());
    assertEquals("Frontend", builtSkill.getCategory());
    assertEquals(12, builtSkill.getYearsOfExperience());
  }

  @Test
  void testGettersAndSetters() {
    Skill testSkill = new Skill();
    testSkill.setId(3L);
    testSkill.setNameEn("React");
    testSkill.setNameFr("React");
    testSkill.setNameEs("React");
    testSkill.setDescriptionEn("React library");
    testSkill.setDescriptionFr("Bibliothèque React");
    testSkill.setDescriptionEs("Biblioteca React");
    testSkill.setProficiencyLevel("Intermediate");
    testSkill.setCategory("Frontend");
    testSkill.setYearsOfExperience(5);
    testSkill.setCreatedAt(LocalDateTime.now());
    testSkill.setUpdatedAt(LocalDateTime.now());

    assertEquals(3L, testSkill.getId());
    assertEquals("React", testSkill.getNameEn());
    assertEquals("Intermediate", testSkill.getProficiencyLevel());
    assertEquals(5, testSkill.getYearsOfExperience());
    assertNotNull(testSkill.getCreatedAt());
    assertNotNull(testSkill.getUpdatedAt());
  }

  @Test
  void testOnCreate() {
    Skill newSkill = new Skill();
    newSkill.setNameEn("Test Skill");
    newSkill.onCreate();

    assertNotNull(newSkill.getCreatedAt());
    assertNotNull(newSkill.getUpdatedAt());
  }

  @Test
  void testOnUpdate() {
    LocalDateTime originalTime = LocalDateTime.now().minusHours(1);
    skill.setUpdatedAt(originalTime);
    skill.onUpdate();

    assertNotEquals(originalTime, skill.getUpdatedAt());
    assertTrue(skill.getUpdatedAt().isAfter(originalTime));
  }

  @Test
  void testEqualsHashCodeToString() {
    Skill another =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(skill.getCreatedAt())
            .updatedAt(skill.getUpdatedAt())
            .build();

    assertEquals(skill, another);
    assertEquals(skill.hashCode(), another.hashCode());
    assertNotNull(skill.toString());
    assertTrue(skill.toString().contains("Java"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    Skill different =
        Skill.builder()
            .id(2L)
            .nameEn("Python")
            .nameFr("Python")
            .nameEs("Python")
            .descriptionEn("Python programming language")
            .descriptionFr("Langage de programmation Python")
            .descriptionEs("Lenguaje de programación Python")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    assertNotEquals(skill, different);
  }

  @Test
  void testEqualsWithNullDescriptions() {
    Skill skill1 =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    Skill skill2 =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .build();

    assertEquals(skill1, skill2);
  }

  @ParameterizedTest
  @MethodSource("provideProficiencyLevels")
  void testEqualsWithDifferentProficiencyLevel(String proficiencyLevel) {
    LocalDateTime now = LocalDateTime.now();
    Skill testSkill =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel(proficiencyLevel)
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Skill baseSkill =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!proficiencyLevel.equals("Expert")) {
      assertNotEquals(baseSkill, testSkill);
    } else {
      assertEquals(baseSkill, testSkill);
    }
  }

  @ParameterizedTest
  @MethodSource("provideYearsOfExperience")
  void testEqualsWithDifferentYearsOfExperience(Integer yearsOfExperience) {
    LocalDateTime now = LocalDateTime.now();
    Skill testSkill =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(yearsOfExperience)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Skill baseSkill =
        Skill.builder()
            .id(1L)
            .nameEn("Java")
            .nameFr("Java")
            .nameEs("Java")
            .descriptionEn("Java programming language")
            .descriptionFr("Langage de programmation Java")
            .descriptionEs("Lenguaje de programación Java")
            .proficiencyLevel("Expert")
            .category("Backend")
            .yearsOfExperience(10)
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!yearsOfExperience.equals(10)) {
      assertNotEquals(baseSkill, testSkill);
    } else {
      assertEquals(baseSkill, testSkill);
    }
  }

  private static Stream<String> provideProficiencyLevels() {
    return Stream.of("Beginner", "Intermediate", "Advanced", "Expert");
  }

  private static Stream<Integer> provideYearsOfExperience() {
    return Stream.of(0, 1, 5, 10, 20);
  }
}
