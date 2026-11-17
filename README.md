# TunasCare

Mini project pencatat tanaman dan aktivitas perawatannya. Aplikasi dibagi menjadi dua Spring Boot microservices dengan database terpisah:

- **catalog-service**: membuat dan membaca profil tanaman.
- **care-service**: mencatat penyiraman/pemupukan, memvalidasi tanaman melalui HTTP ke `catalog-service`, dan menyediakan rekap berbasis native SQL.

## Teknologi

- Java 21, Spring Boot 3.3, Spring Web, Spring Data JPA, Bean Validation
- PostgreSQL 16
- Docker Compose

Dependency injection menggunakan constructor injection yang dikelola Spring. Hasil repository diubah menjadi DTO menggunakan Java Stream (`map` dan `toList`). Rekap memanfaatkan CTE, `ROW_NUMBER()` window function, agregasi kondisional, dan parameter rentang waktu PostgreSQL.

## Menjalankan

Jalankan semua service dan database dengan Docker Compose dari folder ini:

```bash
docker compose up --build
```

Endpoint tersedia di `http://localhost:8081` (catalog) dan `http://localhost:8082` (care). Untuk menjalankan test/build tanpa Docker:

```bash
mvn test
```

Saat dijalankan langsung, PostgreSQL harus tersedia di port `5433` (catalog) dan `5434` (care). URL database dan alamat antar-service dapat diubah lewat environment variables yang didefinisikan di `application.properties`.

## API

### Membuat profil tanaman

```http
POST /api/plants
Content-Type: application/json

{
  "name": "Monstera",
  "species": "Monstera deliciosa",
  "location": "Ruang keluarga"
}
```

`GET /api/plants` menampilkan semua tanaman; `GET /api/plants/{id}` mengambil satu profil.

### Mencatat perawatan

```http
POST /api/care-logs
Content-Type: application/json

{
  "plantId": 1,
  "type": "WATER",
  "notes": "Disiram sampai media lembap"
}
```

`type` yang tersedia: `WATER` dan `FERTILIZE`. `caredAt` opsional, format ISO-8601 dengan zona waktu, misalnya `2026-11-17T09:30:00Z`.

`GET /api/plants/{plantId}/care-logs` menampilkan riwayat tanaman tersebut.

### Rekap aktivitas

```http
GET /api/care-logs/summary?from=2026-11-01T00:00:00Z&to=2026-12-01T00:00:00Z
```

Parameter `from` inklusif dan `to` eksklusif. Setiap baris berisi jumlah total aktivitas, jumlah per jenis, serta waktu terakhir penyiraman dan pemupukan untuk tiap tanaman yang memiliki aktivitas pada rentang tersebut.
