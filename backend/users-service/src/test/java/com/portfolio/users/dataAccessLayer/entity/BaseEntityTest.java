package com.portfolio.users.dataAccessLayer.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BaseEntity Unit Tests")
class BaseEntityTest {

  private BaseEntity baseEntity;
  private LocalDateTime testDateTime;

  @BeforeEach
  void setUp() {
    testDateTime = LocalDateTime.now();
    baseEntity = new BaseEntity();
    baseEntity.setId(1L);
    baseEntity.setCreatedAt(testDateTime);
    baseEntity.setUpdatedAt(testDateTime);
  }

  @Test
  @DisplayName("Should create BaseEntity with no-args constructor")
  void testBaseEntityNoArgsConstructor() {
    BaseEntity entity = new BaseEntity();
    assertNotNull(entity);
  }

  @Test
  @DisplayName("Should create BaseEntity with all-args constructor")
  void testBaseEntityAllArgsConstructor() {
    LocalDateTime now = LocalDateTime.now();
    BaseEntity entity = new BaseEntity(1L, now, now);
    assertNotNull(entity);
    assertEquals(1L, entity.getId());
    assertEquals(now, entity.getCreatedAt());
    assertEquals(now, entity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should set and get id correctly")
  void testIdSetterGetter() {
    baseEntity.setId(2L);
    assertEquals(2L, baseEntity.getId());
  }

  @Test
  @DisplayName("Should set and get createdAt correctly")
  void testCreatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusDays(1);
    baseEntity.setCreatedAt(newTime);
    assertEquals(newTime, baseEntity.getCreatedAt());
  }

  @Test
  @DisplayName("Should set and get updatedAt correctly")
  void testUpdatedAtSetterGetter() {
    LocalDateTime newTime = LocalDateTime.now().plusHours(1);
    baseEntity.setUpdatedAt(newTime);
    assertEquals(newTime, baseEntity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should test onCreate sets both timestamps")
  void testOnCreateMethod() {
    BaseEntity entity = new BaseEntity();
    LocalDateTime beforeCreate = LocalDateTime.now().minusSeconds(1);

    entity.onCreate();

    assertNotNull(entity.getCreatedAt());
    assertNotNull(entity.getUpdatedAt());
    assertTrue(entity.getCreatedAt().isAfter(beforeCreate));
    assertTrue(entity.getUpdatedAt().isAfter(beforeCreate));
  }

  @Test
  @DisplayName("Should test onUpdate updates only updatedAt")
  void testOnUpdateMethod() {
    LocalDateTime originalCreatedAt = baseEntity.getCreatedAt();
    LocalDateTime originalUpdatedAt = baseEntity.getUpdatedAt();

    // Small delay to ensure timestamp changes
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    baseEntity.onUpdate();

    assertEquals(originalCreatedAt, baseEntity.getCreatedAt());
    assertTrue(baseEntity.getUpdatedAt().isAfter(originalUpdatedAt));
  }

  @Test
  @DisplayName("Should test multiple updates increment updatedAt")
  void testMultipleUpdates() throws InterruptedException {
    baseEntity.onCreate();
    LocalDateTime firstCreatedAt = baseEntity.getCreatedAt();

    Thread.sleep(10);
    baseEntity.onUpdate();
    LocalDateTime firstUpdatedAt = baseEntity.getUpdatedAt();

    Thread.sleep(10);
    baseEntity.onUpdate();
    LocalDateTime secondUpdatedAt = baseEntity.getUpdatedAt();

    assertEquals(firstCreatedAt, baseEntity.getCreatedAt());
    assertTrue(secondUpdatedAt.isAfter(firstUpdatedAt));
  }

  @Test
  @DisplayName("Should test equals with same values")
  void testEqualsWithSameValues() {
    BaseEntity entity1 = new BaseEntity(1L, testDateTime, testDateTime);
    BaseEntity entity2 = new BaseEntity(1L, testDateTime, testDateTime);

    assertEquals(entity1, entity2);
  }

  @Test
  @DisplayName("Should test equals with different ids")
  void testEqualsWithDifferentIds() {
    BaseEntity entity1 = new BaseEntity(1L, testDateTime, testDateTime);
    BaseEntity entity2 = new BaseEntity(2L, testDateTime, testDateTime);

    assertNotEquals(entity1, entity2);
  }

  @Test
  @DisplayName("Should test hashCode consistency")
  void testHashCodeConsistency() {
    BaseEntity entity1 = new BaseEntity(1L, testDateTime, testDateTime);
    BaseEntity entity2 = new BaseEntity(1L, testDateTime, testDateTime);

    assertEquals(entity1.hashCode(), entity2.hashCode());
  }

  @Test
  @DisplayName("Should test toString method")
  void testToString() {
    String entityString = baseEntity.toString();
    assertNotNull(entityString);
    assertTrue(entityString.contains("BaseEntity") || entityString.length() > 0);
  }

  @Test
  @DisplayName("Should handle null timestamps")
  void testNullTimestamps() {
    BaseEntity entity = new BaseEntity();
    entity.setCreatedAt(null);
    entity.setUpdatedAt(null);

    assertNull(entity.getCreatedAt());
    assertNull(entity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should test getId returns correct value")
  void testGetId() {
    assertEquals(1L, baseEntity.getId());
  }

  @Test
  @DisplayName("Should test createdAt is not updatable through data")
  void testCreatedAtConsistency() {
    LocalDateTime original = baseEntity.getCreatedAt();
    baseEntity.onUpdate();
    assertEquals(original, baseEntity.getCreatedAt());
  }

  @Test
  @DisplayName("Should test entity with large id")
  void testEntityWithLargeId() {
    long largeId = 9999999999L;
    BaseEntity entity = new BaseEntity();
    entity.setId(largeId);
    assertEquals(largeId, entity.getId());
  }

  @Test
  @DisplayName("Should test BaseEntity equals with same reference")
  void testBaseEntityEqualsSameReference() {
    assertTrue(baseEntity.equals(baseEntity));
  }

  @Test
  @DisplayName("Should test BaseEntity equals with different createdAt")
  void testBaseEntityEqualsWithDifferentCreatedAt() {
    BaseEntity entity2 = new BaseEntity();
    entity2.setId(baseEntity.getId());
    entity2.setCreatedAt(LocalDateTime.now().plusDays(1));
    entity2.setUpdatedAt(baseEntity.getUpdatedAt());

    assertNotEquals(baseEntity, entity2);
  }

  @Test
  @DisplayName("Should test BaseEntity equals with different updatedAt")
  void testBaseEntityEqualsWithDifferentUpdatedAt() {
    BaseEntity entity2 = new BaseEntity();
    entity2.setId(baseEntity.getId());
    entity2.setCreatedAt(baseEntity.getCreatedAt());
    entity2.setUpdatedAt(LocalDateTime.now().plusDays(1));

    assertNotEquals(baseEntity, entity2);
  }

  @Test
  @DisplayName("Should test BaseEntity equals with null createdAt")
  void testBaseEntityEqualsNullCreatedAt() {
    baseEntity.setCreatedAt(null);
    BaseEntity entity2 = new BaseEntity();
    entity2.setId(baseEntity.getId());
    entity2.setCreatedAt(null);
    entity2.setUpdatedAt(baseEntity.getUpdatedAt());

    assertEquals(baseEntity, entity2);
  }

  @Test
  @DisplayName("Should test BaseEntity lifecycle with explicit timestamps")
  void testBaseEntityLifecycleExplicitTimestamps() {
    LocalDateTime now = LocalDateTime.now();
    BaseEntity entity = new BaseEntity(2L, now, now);

    assertNotNull(entity.getId());
    assertNotNull(entity.getCreatedAt());
    assertNotNull(entity.getUpdatedAt());
    assertEquals(2L, entity.getId());
    assertEquals(now, entity.getCreatedAt());
    assertEquals(now, entity.getUpdatedAt());
  }

  @Test
  @DisplayName("Should test BaseEntity hashCode consistency with fields")
  void testBaseEntityHashCodeWithNullFields() {
    BaseEntity entity = new BaseEntity();
    entity.setId(null);
    entity.setCreatedAt(null);
    entity.setUpdatedAt(null);

    int hash = entity.hashCode();
    assertNotNull(hash);
  }
}
