package com.portfolio.hobbies.mappingLayer.dto;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class HobbyDTOTest {

  private HobbyDTO hobbyDTO;

  @BeforeEach
  void setUp() {
    hobbyDTO = new HobbyDTO();
  }

  @Test
  void testNoArgsConstructor() {
    HobbyDTO testDTO = new HobbyDTO();

    assertNotNull(testDTO);
    assertNull(testDTO.getId());
    assertNull(testDTO.getNameEn());
    assertNull(testDTO.getNameFr());
    assertNull(testDTO.getNameEs());
  }

  @Test
  void testAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO =
        new HobbyDTO(
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

    assertNotNull(testDTO);
    assertEquals(1L, testDTO.getId());
    assertEquals("Reading", testDTO.getNameEn());
    assertEquals("Lecture", testDTO.getNameFr());
    assertEquals("Lectura", testDTO.getNameEs());
    assertEquals("English description", testDTO.getDescriptionEn());
    assertEquals("French description", testDTO.getDescriptionFr());
    assertEquals("Spanish description", testDTO.getDescriptionEs());
    assertEquals("icon.png", testDTO.getIcon());
    assertEquals(now, testDTO.getCreatedAt());
    assertEquals(now, testDTO.getUpdatedAt());
  }

  @Test
  void testBuilder() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO =
        HobbyDTO.builder()
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

    assertEquals(2L, testDTO.getId());
    assertEquals("Gaming", testDTO.getNameEn());
    assertEquals("Jeux", testDTO.getNameFr());
    assertEquals("Juegos", testDTO.getNameEs());
  }

  @Test
  void testGettersAndSetters() {
    LocalDateTime now = LocalDateTime.now();

    hobbyDTO.setId(3L);
    hobbyDTO.setNameEn("Coding");
    hobbyDTO.setNameFr("Codage");
    hobbyDTO.setNameEs("Codificación");
    hobbyDTO.setDescriptionEn("Programming");
    hobbyDTO.setDescriptionFr("Programmation");
    hobbyDTO.setDescriptionEs("Programación");
    hobbyDTO.setIcon("code.png");
    hobbyDTO.setCreatedAt(now);
    hobbyDTO.setUpdatedAt(now);

    assertEquals(3L, hobbyDTO.getId());
    assertEquals("Coding", hobbyDTO.getNameEn());
    assertEquals("Codage", hobbyDTO.getNameFr());
    assertEquals("Codificación", hobbyDTO.getNameEs());
    assertEquals("Programming", hobbyDTO.getDescriptionEn());
    assertEquals("Programmation", hobbyDTO.getDescriptionFr());
    assertEquals("Programación", hobbyDTO.getDescriptionEs());
    assertEquals("code.png", hobbyDTO.getIcon());
    assertEquals(now, hobbyDTO.getCreatedAt());
    assertEquals(now, hobbyDTO.getUpdatedAt());
  }

  @Test
  void testValidDTO() {
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Sports")
            .nameFr("Sports")
            .nameEs("Deportes")
            .descriptionEn("Playing sports")
            .icon("sports.png")
            .build();

    assertNotNull(testDTO);
    assertEquals("Sports", testDTO.getNameEn());
    assertEquals("Sports", testDTO.getNameFr());
    assertEquals("Deportes", testDTO.getNameEs());
  }

  @Test
  void testNameEnNotBlank() {
    HobbyDTO testDTO =
        HobbyDTO.builder().id(1L).nameEn("NotBlank").nameFr("Test").nameEs("Test").build();

    assertNotNull(testDTO.getNameEn());
    assertFalse(testDTO.getNameEn().isEmpty());
    assertFalse(testDTO.getNameEn().isBlank());
  }

  @Test
  void testNameEnNull() {
    HobbyDTO testDTO = HobbyDTO.builder().id(1L).nameEn(null).nameFr("Test").nameEs("Test").build();

    assertNull(testDTO.getNameEn());
  }

  @Test
  void testNameFrNotBlank() {
    HobbyDTO testDTO =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("NotBlank").nameEs("Test").build();

    assertNotNull(testDTO.getNameFr());
    assertFalse(testDTO.getNameFr().isEmpty());
  }

  @Test
  void testNameEsNotBlank() {
    HobbyDTO testDTO =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("NotBlank").build();

    assertNotNull(testDTO.getNameEs());
    assertFalse(testDTO.getNameEs().isEmpty());
  }

  @Test
  void testDescriptionsCanBeNull() {
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .build();

    assertNull(testDTO.getDescriptionEn());
    assertNull(testDTO.getDescriptionFr());
    assertNull(testDTO.getDescriptionEs());
  }

  @Test
  void testIconCanBeNull() {
    HobbyDTO testDTO =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    assertNull(testDTO.getIcon());
  }

  @Test
  void testEqualsHashCodeToString() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO dto1 =
        HobbyDTO.builder()
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

    HobbyDTO dto2 =
        HobbyDTO.builder()
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

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotNull(dto1.toString());
    assertTrue(dto1.toString().contains("HobbyDTO"));
  }

  @Test
  void testEqualsWithFieldDifferences() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO dto1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO dto2 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Art")
            .nameEs("Arte")
            .icon("different.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertNotEquals(dto1, dto2);
  }

  @Test
  void testEqualsWithNullDescriptions() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO dto1 =
        HobbyDTO.builder()
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

    HobbyDTO dto2 =
        HobbyDTO.builder()
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

    assertEquals(dto1, dto2);
  }

  private static Stream<String> provideNameEn() {
    return Stream.of("Reading", "Gaming", "Cooking", "Traveling", "Photography");
  }

  @ParameterizedTest
  @MethodSource("provideNameEn")
  void testEqualsWithDifferentNameEn(String nameEn) {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn(nameEn)
            .nameFr("Name")
            .nameEs("Nombre")
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO baseDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Art")
            .nameFr("Name")
            .nameEs("Nombre")
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (!nameEn.equals("Art")) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  private static Stream<String> provideIcons() {
    return Stream.of("icon1.png", "icon2.svg", "icon3.jpg", null);
  }

  @ParameterizedTest
  @MethodSource("provideIcons")
  void testEqualsWithDifferentIcons(String icon) {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Music")
            .nameFr("Musique")
            .nameEs("Música")
            .icon(icon)
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO baseDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Music")
            .nameFr("Musique")
            .nameEs("Música")
            .icon("base.png")
            .createdAt(now)
            .updatedAt(now)
            .build();

    if (icon == null || !icon.equals("base.png")) {
      assertNotEquals(baseDTO, testDTO);
    } else {
      assertEquals(baseDTO, testDTO);
    }
  }

  @Test
  void testMultipleDTOs() {
    HobbyDTO dto1 =
        HobbyDTO.builder().id(1L).nameEn("Reading").nameFr("Lecture").nameEs("Lectura").build();

    HobbyDTO dto2 =
        HobbyDTO.builder().id(2L).nameEn("Gaming").nameFr("Jeux").nameEs("Juegos").build();

    HobbyDTO dto3 =
        HobbyDTO.builder().id(3L).nameEn("Cooking").nameFr("Cuisine").nameEs("Cocina").build();

    assertNotEquals(dto1, dto2);
    assertNotEquals(dto2, dto3);
    assertNotEquals(dto1, dto3);
    assertEquals(1L, dto1.getId());
    assertEquals(2L, dto2.getId());
    assertEquals(3L, dto3.getId());
  }

  @Test
  void testDTOWithAllDescriptionsNull() {
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn(null)
            .descriptionFr(null)
            .descriptionEs(null)
            .icon(null)
            .build();

    assertNull(testDTO.getDescriptionEn());
    assertNull(testDTO.getDescriptionFr());
    assertNull(testDTO.getDescriptionEs());
    assertNull(testDTO.getIcon());
  }

  @Test
  void testDTOFieldAssignmentIndividually() {
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setId(100L);
    testDTO.setNameEn("HobbyName");
    testDTO.setDescriptionEn("Very long description that covers many aspects");

    assertTrue(testDTO.getId() > 0);
    assertNotNull(testDTO.getNameEn());
    assertNotNull(testDTO.getDescriptionEn());
  }

  @Test
  void testNameFrAndNameEs() {
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setNameFr("French Name");
    testDTO.setNameEs("Spanish Name");

    assertEquals("French Name", testDTO.getNameFr());
    assertEquals("Spanish Name", testDTO.getNameEs());
  }

  @Test
  void testDescriptionLanguages() {
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setDescriptionEn("English desc");
    testDTO.setDescriptionFr("French desc");
    testDTO.setDescriptionEs("Spanish desc");

    assertEquals("English desc", testDTO.getDescriptionEn());
    assertEquals("French desc", testDTO.getDescriptionFr());
    assertEquals("Spanish desc", testDTO.getDescriptionEs());
  }

  @Test
  void testIconField() {
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setIcon("hobby_icon.svg");

    assertEquals("hobby_icon.svg", testDTO.getIcon());
  }

  @Test
  void testTimestampFields() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setCreatedAt(now);
    testDTO.setUpdatedAt(now);

    assertEquals(now, testDTO.getCreatedAt());
    assertEquals(now, testDTO.getUpdatedAt());
  }

  @Test
  void testDTOWithPartialNullFields() {
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(5L)
            .nameEn("Partial")
            .nameFr("Partiel")
            .nameEs("Parcial")
            .descriptionEn("Has description")
            .descriptionFr(null)
            .descriptionEs(null)
            .icon(null)
            .build();

    assertNotNull(testDTO.getDescriptionEn());
    assertNull(testDTO.getDescriptionFr());
    assertNull(testDTO.getDescriptionEs());
    assertNull(testDTO.getIcon());
  }

  @Test
  void testDTOBuilderChaining() {
    HobbyDTO testDTO =
        HobbyDTO.builder()
            .id(10L)
            .nameEn("Chained")
            .nameFr("Chaîné")
            .nameEs("Encadenado")
            .icon("chain.png")
            .build();

    assertNotNull(testDTO);
    assertEquals(10L, testDTO.getId());
    assertEquals("Chained", testDTO.getNameEn());
  }

  @Test
  void testComparisonWithSameId() {
    HobbyDTO dto1 = HobbyDTO.builder().id(1L).nameEn("Same").nameFr("Same").nameEs("Same").build();

    HobbyDTO dto2 = HobbyDTO.builder().id(1L).nameEn("Same").nameFr("Same").nameEs("Same").build();

    assertEquals(dto1, dto2);
  }

  @Test
  void testComparisonWithDifferentId() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO dto1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Same")
            .nameFr("Same")
            .nameEs("Same")
            .createdAt(now)
            .updatedAt(now)
            .build();

    HobbyDTO dto2 =
        HobbyDTO.builder()
            .id(2L)
            .nameEn("Same")
            .nameFr("Same")
            .nameEs("Same")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertNotEquals(dto1, dto2);
  }

  @Test
  void testLanguageNames() {
    String[] languages = {"English", "French", "Spanish"};
    HobbyDTO testDTO = new HobbyDTO();

    testDTO.setNameEn(languages[0]);
    testDTO.setNameFr(languages[1]);
    testDTO.setNameEs(languages[2]);

    assertEquals("English", testDTO.getNameEn());
    assertEquals("French", testDTO.getNameFr());
    assertEquals("Spanish", testDTO.getNameEs());
  }

  @Test
  void testDTOIdSetterGetter() {
    HobbyDTO testDTO = new HobbyDTO();
    Long testId = 500L;
    testDTO.setId(testId);

    assertEquals(testId, testDTO.getId());
  }

  @Test
  void testDTOLongDescriptionFields() {
    String longDesc =
        "This is a very long description that could potentially reach the 2000 character limit";
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setDescriptionEn(longDesc);
    testDTO.setDescriptionFr(longDesc);
    testDTO.setDescriptionEs(longDesc);

    assertEquals(longDesc, testDTO.getDescriptionEn());
    assertEquals(longDesc, testDTO.getDescriptionFr());
    assertEquals(longDesc, testDTO.getDescriptionEs());
  }

  @Test
  void testDTOEqualsWithNullFields() {
    HobbyDTO h1 =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    HobbyDTO h2 =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    assertEquals(h1, h2);
  }

  @Test
  void testDTOEqualsWithOneNullIcon() {
    HobbyDTO h1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .icon("icon.png")
            .build();

    HobbyDTO h2 =
        HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").icon(null).build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testDTOLongIdField() {
    Long largeId = 9999999999L;
    HobbyDTO testDTO =
        HobbyDTO.builder().id(largeId).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertEquals(largeId, testDTO.getId());
    assertTrue(testDTO.getId() > 0);
  }

  @Test
  void testDTOTimestampFields() {
    LocalDateTime now = LocalDateTime.now();
    HobbyDTO testDTO = new HobbyDTO();
    testDTO.setCreatedAt(now);
    testDTO.setUpdatedAt(now);

    assertEquals(now, testDTO.getCreatedAt());
    assertEquals(now, testDTO.getUpdatedAt());
  }

  @Test
  void testDTOEqualsWithDifferentDescriptionEn() {
    HobbyDTO h1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn("Desc1")
            .build();
    HobbyDTO h2 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEn("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testDTOEqualsWithDifferentDescriptionFr() {
    HobbyDTO h1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionFr("Desc1")
            .build();
    HobbyDTO h2 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionFr("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testDTOEqualsWithDifferentDescriptionEs() {
    HobbyDTO h1 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEs("Desc1")
            .build();
    HobbyDTO h2 =
        HobbyDTO.builder()
            .id(1L)
            .nameEn("Test")
            .nameFr("Test")
            .nameEs("Test")
            .descriptionEs("Desc2")
            .build();

    assertNotEquals(h1, h2);
  }

  @Test
  void testDTOHashCodeConsistency() {
    HobbyDTO h1 = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    HobbyDTO h2 = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertEquals(h1.hashCode(), h2.hashCode());
  }

  @Test
  void testDTOEqualsWithNullObject() {
    HobbyDTO dto = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    assertNotEquals(dto, null);
  }

  @Test
  void testDTOEqualsWithDifferentType() {
    HobbyDTO dto = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    Object obj = "Not a hobby";
    assertNotEquals(dto, obj);
  }

  @Test
  void testDTOCanEqual() {
    HobbyDTO h1 = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();
    HobbyDTO h2 = HobbyDTO.builder().id(1L).nameEn("Test").nameFr("Test").nameEs("Test").build();

    assertTrue(h1.canEqual(h2));
    assertTrue(h2.canEqual(h1));
  }
}
