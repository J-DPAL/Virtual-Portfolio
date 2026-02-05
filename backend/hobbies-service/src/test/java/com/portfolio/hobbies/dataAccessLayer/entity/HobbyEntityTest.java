package com.portfolio.hobbies.dataAccessLayer.entity;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class HobbyEntityTest {

  private Hobby hobby;

  @BeforeEach
  void setUp() {
    hobby = new Hobby();
  }

  @Test
  void testNoArgsConstructor() {
    Hobby testHobby = new Hobby();

    assertNotNull(testHobby);
    assertNull(testHobby.getId());
    assertNull(testHobby.getNameEn());
    assertNull(testHobby.getNameFr());
    assertNull(testHobby.getNameEs());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    Hobby testHobby =
        new Hobby(
            1L,
            "Reading",
            "Lecture",
            "Lectura",
            "English description",
            "French description",
            "Spanish description",
            "icon.png",
            now,
            now);

    assertNotNull(testHobby);
    assertEquals(1L, testHobby.getId());
    assertEquals("Reading", testHobby.getNameEn());
    assertEquals("Lecture", testHobby.getNameFr());
    assertEquals("Lectura", testHobby.getNameEs());
    assertEquals("English description", testHobby.getDescriptionEn());
    assertEquals("French description", testHobby.getDescriptionFr());
    assertEquals("Spanish description", testHobby.getDescriptionEs());
    assertEquals("icon.png", testHobby.getIcon());
    assertEquals(now, testHobby.getCreatedAt());
    assertEquals(now, testHobby.getUpdatedAt());
  }

  @Test
  void testBuilder() {
    LocalDateTime now = LocalDateTime.now();
    Hobby testHobby =
        Hobby.builder()
            .id(2L)
            .nameEn("Gaming")
            .nameFr("Jeux")
            .nameEs("Juegos")
            .descriptionEn("Video gaming")
            .descriptionFr("Jeux vidéo")
            .descriptionEs("Videojuegos")
            .icon("game.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertEquals(2L, testHobby.getId());
    assertEquals("Gaming", testHobby.getNameEn());
    assertEquals("Jeux", testHobby.getNameFr());
    assertEquals("Juegos", testHobby.getNameEs());
  }

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();

    hobby.setId(3L);
    hobby.setNameEn("Coding");
    hobby.setNameFr("Codage");
    hobby.setNameEs("Codificación");
    hobby.setDescriptionEn("Programming");
    hobby.setDescriptionFr("Programmation");
    hobby.setDescriptionEs("Programación");
    hobby.setIcon("code.png");
    hobby.setCreatedAt(now);
    hobby.setUpdatedAt(now);

    assertEquals(3L, hobby.getId());
    assertEquals("Coding", hobby.getNameEn());
    assertEquals("Codage", hobby.getNameFr());
    assertEquals("Codificación", hobby.getNameEs());
    assertEquals("Programming", hobby.getDescriptionEn());
    assertEquals("Programmation", hobby.getDescriptionFr());
    assertEquals("Programación", hobby.getDescriptionEs());
    assertEquals("code.png", hobby.getIcon());
    assertEquals(now, hobby.getCreatedAt());
    assertEquals(now, hobby.getUpdatedAt());
  }

  @Test
  void testOnCreate() {
    hobby.setNameEn("Music");
    hobby.setNameFr("Musique");
    hobby.setNameEs("Música");

    hobby.onCreate();

    assertNotNull(hobby.getCreatedAt());
    assertNotNull(hobby.getUpdatedAt());
    assertTrue(hobby.getCreatedAt().compareTo(hobby.getUpdatedAt()) <= 0);
  }

  @Test
  void testOnUpdate() {
    LocalDateTime initialTime = LocalDateTime.now();
    hobby.setCreatedAt(initialTime);
    hobby.setUpdatedAt(initialTime);

    hobby.onUpdate();

    assertEquals(initialTime, hobby.getCreatedAt());
    assertNotNull(hobby.getUpdatedAt());
    assertTrue(
        hobby.getUpdatedAt().isAfter(initialTime) || hobby.getUpdatedAt().isEqual(initialTime));
  }

  @Test
  void testEqualsHashCodeToString() {
    LocalDateTime now = LocalDateTime.now();
    Hobby hobby1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .descriptionEn("Art hobby")
            .descriptionFr("Loisir artistique")
            .descriptionEs("Hobby artístico")
            .icon("art.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby hobby2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .descriptionEn("Art hobby")
            .descriptionFr("Loisir artistique")
            .descriptionEs("Hobby artístico")
            .icon("art.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertEquals(hobby1, hobby2);
    assertEquals(hobby1.hashCode(), hobby2.hashCode());
    assertNotNull(hobby1.toString());
    assertTrue(hobby1.toString().contains("Hobby"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    LocalDateTime now = LocalDateTime.now();
    Hobby hobby1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby hobby2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .icon("different.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertNotEquals(hobby1, hobby2);
  }

  @Test
  void testEqualsWithNullDescriptions() {
    LocalDateTime now = LocalDateTime.now();
    Hobby hobby1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Sport")
            .nameFr("Sport")
            .nameEs("Deporte")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby hobby2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Sport")
            .nameFr("Sport")
            .nameEs("Deporte")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertEquals(hobby1, hobby2);
  }

  private static Stream<String> provideNameEn() {
    return Stream.of("Reading", "Gaming", "Cooking", "Traveling", "Photography");
  }

  @ParameterizedTest
  @MethodSource("provideNameEn")
  void testEqualsWithDifferentNameEn(String nameEn) {
    LocalDateTime now = LocalDateTime.now();
    Hobby testHobby =
        Hobby.builder()
            .id(1L)
            .nameEn(nameEn)
            .nameFr("Name")
            .nameEs("Nombre")
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby baseHobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Name")
            .nameEs("Nombre")
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!nameEn.equals("Art")) {
      assertNotEquals(baseHobby, testHobby);
    } else {
      assertEquals(baseHobby, testHobby);
    }
  }

  private static Stream<String> provideIcons() {
    return Stream.of("icon1.png", "icon2.svg", "icon3.jpg", null);
  }

  @ParameterizedTest
  @MethodSource("provideIcons")
  void testEqualsWithDifferentIcons(String icon) {
    LocalDateTime now = LocalDateTime.now();
    Hobby testHobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Music")
            .nameFr("Musique")
            .nameEs("Música")
            .icon(icon)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby baseHobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Music")
            .nameFr("Musique")
            .nameEs("Música")
            .icon("base.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (icon == null || !icon.equals("base.png")) {
      assertNotEquals(baseHobby, testHobby);
    } else {
      assertEquals(baseHobby, testHobby);
    }
  }

  @Test
  void testMultipleHobbies() {
    Hobby hobby1 =
        Hobby.builder().id(1L).nameEn("Reading").nameFr("Lecture").nameEs("Lectura").build();

    Hobby hobby2 = Hobby.builder().id(2L).nameEn("Gaming").nameFr("Jeux").nameEs("Juegos").build();

    Hobby hobby3 =
        Hobby.builder().id(3L).nameEn("Cooking").nameFr("Cuisine").nameEs("Cocina").build();

    assertNotEquals(hobby1, hobby2);
    assertNotEquals(hobby2, hobby3);
    assertNotEquals(hobby1, hobby3);
    assertEquals(1L, hobby1.getId());
    assertEquals(2L, hobby2.getId());
    assertEquals(3L, hobby3.getId());
  }

  @Test
  void testHobbyWithAllDescriptionsNull() {
    Hobby testHobby =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .icon(null)
            .build();

    assertNull(testHobby.getDescriptionEn());
    assertNull(testHobby.getDescriptionFr());
    assertNull(testHobby.getDescriptionEs());
    assertNull(testHobby.getIcon());
  }

  @Test
  void testHobbyFieldAssignmentIndividually() {
    Hobby testHobby = new Hobby();
    testHobby.setId(100L);
    testHobby.setNameEn("HobbyName");
    testHobby.setDescriptionEn("Very long description that covers many aspects");

    assertTrue(testHobby.getId() > 0);
    assertNotNull(testHobby.getNameEn());
    assertNotNull(testHobby.getDescriptionEn());
  }

  @Test
  void testNameFrAndNameEs() {
    Hobby testHobby = new Hobby();
    testHobby.setNameFr("French Name");
    testHobby.setNameEs("Spanish Name");

    assertEquals("French Name", testHobby.getNameFr());
    assertEquals("Spanish Name", testHobby.getNameEs());
  }

  @Test
  void testDescriptionLanguages() {
    Hobby testHobby = new Hobby();
    testHobby.setDescriptionEn("English desc");
    testHobby.setDescriptionFr("French desc");
    testHobby.setDescriptionEs("Spanish desc");

    assertEquals("English desc", testHobby.getDescriptionEn());
    assertEquals("French desc", testHobby.getDescriptionFr());
    assertEquals("Spanish desc", testHobby.getDescriptionEs());
  }

  @Test
  void testIconField() {
    Hobby testHobby = new Hobby();
    testHobby.setIcon("hobby_icon.svg");

    assertEquals("hobby_icon.svg", testHobby.getIcon());
  }

  @Test
  void testTimestampFields() {
    LocalDateTime now = LocalDateTime.now();
    Hobby testHobby = new Hobby();
    testHobby.setCreatedAt(now);
    testHobby.setUpdatedAt(now);

    assertEquals(now, testHobby.getCreatedAt());
    assertEquals(now, testHobby.getUpdatedAt());
  }

  @Test
  void testHobbyWithPartialNullFields() {
    Hobby testHobby =
        Hobby.builder()
            .id(5L)
            .nameEn("Partial")
            .nameFr("Partiel")
            .nameEs("Parcial")
            .descriptionEn("Has description")
            .descriptionFr(null)
            .descriptionEs(null)
            .icon(null)
            .build();

    assertNotNull(testHobby.getDescriptionEn());
    assertNull(testHobby.getDescriptionFr());
    assertNull(testHobby.getDescriptionEs());
    assertNull(testHobby.getIcon());
  }

  @Test
  void testHobbyBuilderChaining() {
    Hobby testHobby =
        Hobby.builder()
            .id(10L)
            .nameEn("Chained")
            .nameFr("Chaîné")
            .nameEs("Encadenado")
            .icon("chain.png")
            .build();

    assertNotNull(testHobby);
    assertEquals(10L, testHobby.getId());
    assertEquals("Chained", testHobby.getNameEn());
  }

  @Test
  void testComparisonWithSameId() {
    Hobby hobby1 = Hobby.builder().id(1L).nameEn("Same").nameFr("Same").nameEs("Same").build();

    Hobby hobby2 = Hobby.builder().id(1L).nameEn("Same").nameFr("Same").nameEs("Same").build();

    assertEquals(hobby1, hobby2);
  }

  @Test
  void testComparisonWithDifferentId() {
    LocalDateTime now = LocalDateTime.now();
    Hobby hobby1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Same")
            .nameFr("Same")
            .nameEs("Same")
            .createdAt(now)
            .updatedAt(now)
            .build();

    Hobby hobby2 =
        Hobby.builder()
            .id(2L)
            .nameEn("Same")
            .nameFr("Same")
            .nameEs("Same")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertNotEquals(hobby1, hobby2);
  }

  @Test
  void testLanguageNames() {
    String[] languages = {"English", "French", "Spanish"};
    Hobby testHobby = new Hobby();

    testHobby.setNameEn(languages[0]);
    testHobby.setNameFr(languages[1]);
    testHobby.setNameEs(languages[2]);

    assertEquals("English", testHobby.getNameEn());
    assertEquals("French", testHobby.getNameFr());
    assertEquals("Spanish", testHobby.getNameEs());
  }

  @Test
  void testOnCreateWithMultipleCalls() {
    hobby.onCreate();
    LocalDateTime firstCreated = hobby.getCreatedAt();
    LocalDateTime firstUpdated = hobby.getUpdatedAt();

    hobby.onCreate();
    LocalDateTime secondCreated = hobby.getCreatedAt();
    LocalDateTime secondUpdated = hobby.getUpdatedAt();

    assertNotNull(firstCreated);
    assertNotNull(secondCreated);
  }

  @Test
  void testOnUpdateWithDelayedCall() throws InterruptedException {
    hobby.setCreatedAt(LocalDateTime.now());
    hobby.setUpdatedAt(LocalDateTime.now());
    LocalDateTime beforeUpdate = hobby.getUpdatedAt();

    Thread.sleep(10);
    hobby.onUpdate();
    LocalDateTime afterUpdate = hobby.getUpdatedAt();

    assertTrue(
        afterUpdate.isAfter(beforeUpdate) || afterUpdate.isEqual(beforeUpdate),
        "Updated timestamp should be at or after the previous timestamp");
  }

  @Test
  void testLongIdField() {
    Long largeId = 9999999999L;
    Hobby testHobby =
        Hobby.builder().id(largeId).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertEquals(largeId, testHobby.getId());
    assertTrue(testHobby.getId() > 0);
  }

  @Test
  void testLongDescriptionFields() {
    String longDesc =
        "This is a very long description that could potentially reach the 2000 character limit";
    Hobby testHobby = new Hobby();
    testHobby.setDescriptionEn(longDesc);
    testHobby.setDescriptionFr(longDesc);
    testHobby.setDescriptionEs(longDesc);

    assertEquals(longDesc, testHobby.getDescriptionEn());
    assertEquals(longDesc, testHobby.getDescriptionFr());
    assertEquals(longDesc, testHobby.getDescriptionEs());
  }

  @Test
  void testEqualsWithNullFields() {
    Hobby h1 =
        Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    Hobby h2 =
        Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    assertEquals(h1, h2);
  }

  @Test
  void testEqualsWithOneNullIcon() {
    Hobby h1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .icon("icon.png")
            .build();

    Hobby h2 =
        Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testEqualsWithDifferentDescriptionEn() {
    Hobby h1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn("Desc1")
            .build();
    Hobby h2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testEqualsWithDifferentDescriptionFr() {
    Hobby h1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionFr("Desc1")
            .build();
    Hobby h2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionFr("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testEqualsWithDifferentDescriptionEs() {
    Hobby h1 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEs("Desc1")
            .build();
    Hobby h2 =
        Hobby.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEs("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testHashCodeConsistency() {
    Hobby h1 = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    Hobby h2 = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertEquals(h1.hashCode(), h2.hashCode());
  }

  @Test
  void testEqualsWithNullObject() {
    Hobby hobby = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    assertNotEquals(hobby, null);
  }

  @Test
  void testEqualsWithDifferentType() {
    Hobby hobby = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    Object obj = "Not a hobby";
    assertNotEquals(hobby, obj);
  }

  @Test
  void testCanEqual() {
    Hobby h1 = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    Hobby h2 = Hobby.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertTrue(h1.canEqual(h2));
    assertTrue(h2.canEqual(h1));
  }

  @Test
  void testOnCreateWithNewInstance() {
    Hobby newHobby = new Hobby();
    newHobby.onCreate();

    assertNotNull(newHobby.getCreatedAt());
    assertNotNull(newHobby.getUpdatedAt());
  }
}
