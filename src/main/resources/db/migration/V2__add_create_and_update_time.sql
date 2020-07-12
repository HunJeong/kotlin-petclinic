ALTER TABLE `vets` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `vets` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `specialities` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `specialities` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `vet_specialties` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `vet_specialties` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `types` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `types` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `owners` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `owners` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `pets` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `pets` ADD `created_at` DATETIME NOT NULL;

ALTER TABLE `visits` ADD `updated_at` DATETIME NOT NULL;
ALTER TABLE `visits` ADD `created_at` DATETIME NOT NULL;
