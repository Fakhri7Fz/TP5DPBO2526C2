# TP5 DPBO 2025/2026

## Data Diri

| Keterangan  | Data                                      |
| ----------- | ----------------------------------------- |
| Nama        | Fakhri Fauzan                             |
| NIM         | 2501536                                   |
| Kelas       | C2                                        |
| Mata Kuliah | Desain dan Pemrograman Berorientasi Objek |

# Janji

Saya Fakhri Fauzan dengan NIM 2501536 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek. Untuk keberkahan-Nya, saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain dan Fungsi Program

Program ini merupakan aplikasi pengelolaan data orang (_People Data_) berbasis GUI menggunakan Java Swing. Pada TP5, program dikembangkan dengan menghubungkan aplikasi Java ke database MySQL menggunakan JDBC (_Java Database Connectivity_). Dengan demikian, data tidak lagi dikelola menggunakan `ArrayList`, melainkan disimpan dan diakses melalui database.

Program terdiri dari kelas `Person`, `PeopleMenu`, dan `Database` yang memiliki tanggung jawab berbeda.

### 1. Kelas `Person`

Kelas `Person` digunakan untuk merepresentasikan data seseorang. Kelas ini memiliki atribut berikut:

| Atribut           | Tipe Data | Keterangan                                             |
| ----------------- | --------- | ------------------------------------------------------ |
| `id`              | `String`  | Identitas atau nomor ID seseorang                      |
| `nama`            | `String`  | Nama lengkap seseorang                                 |
| `tahunLahir`      | `int`     | Tahun lahir seseorang                                  |
| `kategori`        | `String`  | Kategori seseorang                                     |
| `statusKeaktifan` | `String`  | Status keaktifan seseorang, yaitu Aktif atau Non-Aktif |

Atribut dibuat menggunakan access modifier `private` untuk menerapkan enkapsulasi. Pengaksesan dan perubahan nilai atribut dilakukan melalui getter dan setter. Kelas ini juga memiliki constructor tanpa parameter dan constructor berparameter untuk membuat objek `Person`.

### 2. Kelas `PeopleMenu`

Kelas `PeopleMenu` digunakan untuk menampilkan antarmuka aplikasi sekaligus mengelola interaksi pengguna dan operasi CRUD pada database.

Antarmuka program menyediakan komponen berikut:

- **ID, Nama, dan Tahun Lahir:** untuk memasukkan identitas seseorang.
- **Kategori:** untuk memilih kategori seseorang melalui combo box.
- **Status Keaktifan:** menggunakan radio button Aktif dan Non-Aktif.
- **Tabel Data:** untuk menampilkan data yang diambil dari database.
- **Tombol Add/Update:** untuk menambahkan data baru atau memperbarui data yang dipilih.
- **Tombol Delete:** untuk menghapus data yang dipilih setelah pengguna memberikan konfirmasi.
- **Tombol Cancel:** untuk membatalkan pengisian atau mengosongkan kembali form.

Kelas ini menjalankan query melalui objek `Database`. Setelah operasi penambahan, pembaruan, atau penghapusan berhasil, tabel dimuat kembali agar menampilkan data terbaru dari database.

### 3. Kelas `Database`

Kelas `Database` digunakan untuk menghubungkan aplikasi Java dengan database MySQL bernama `db_people` menggunakan JDBC.

Kelas ini menyediakan beberapa fungsi utama:

- **Constructor `Database()`:** membuat koneksi ke database MySQL dan menyiapkan `Statement`.
- **`selectQuery()`:** menjalankan query `SELECT` untuk mengambil data dari database.
- **`insertUpdateDeleteQuery()`:** menjalankan query `INSERT`, `UPDATE`, dan `DELETE`.
- **`getStatement()`:** menyediakan akses ke objek `Statement`.

Database menggunakan tabel `person` dengan struktur berikut:

| Kolom              | Tipe Data      | Keterangan                   |
| ------------------ | -------------- | ---------------------------- |
| `id`               | `VARCHAR(255)` | ID seseorang dan primary key |
| `nama`             | `VARCHAR(255)` | Nama lengkap                 |
| `thn_lahir`        | `INT`          | Tahun lahir                  |
| `kategori`         | `VARCHAR(255)` | Kategori seseorang           |
| `status_keaktifan` | `VARCHAR(50)`  | Status keaktifan             |

Struktur tabel dan data awal disediakan dalam file `db_people.sql`. File tersebut dapat digunakan untuk membuat ulang tabel dan memasukkan data awal ke database.

### 4. Fungsi Utama Program

Program menyediakan seluruh operasi CRUD (_Create, Read, Update, Delete_) sebagai berikut:

- **Create:** menambahkan data orang baru ke database berdasarkan input pengguna.
- **Read:** mengambil data dari database dan menampilkannya pada tabel aplikasi.
- **Update:** memperbarui data orang yang dipilih pada database.
- **Delete:** menghapus data orang yang dipilih setelah pengguna memberikan konfirmasi.

Program juga menyediakan validasi input untuk mencegah data yang tidak sesuai, seperti field wajib yang kosong, tahun lahir yang tidak valid, dan ID duplikat. Tahun lahir dibatasi pada rentang 1900–2026. Validasi dilakukan sebelum data ditambahkan atau diperbarui.

# Alur Program

1. Pengguna menjalankan aplikasi Java Swing ketika server MySQL sudah aktif.
2. Kelas `Database` membuat koneksi ke database `db_people`.
3. Kelas `PeopleMenu` menjalankan query `SELECT` untuk mengambil data dari tabel `person` dan menampilkannya pada JTable.
4. Untuk menambahkan data, pengguna mengisi seluruh field yang diperlukan, memilih kategori dan status keaktifan, kemudian menekan tombol Add.
5. Program memvalidasi input. Jika terdapat input yang tidak valid atau ID sudah digunakan, program menampilkan dialog peringatan atau error. Jika valid, data disimpan ke database dan tabel diperbarui.
6. Untuk memperbarui data, pengguna memilih baris pada tabel. Data dari baris tersebut ditampilkan pada form. Setelah pengguna melakukan perubahan dan menekan Update, program memvalidasi input lalu memperbarui data pada database.
7. Untuk menghapus data, pengguna memilih baris yang diinginkan dan menekan Delete. Program menampilkan dialog konfirmasi. Data hanya dihapus apabila pengguna menyetujui konfirmasi tersebut.
8. Setelah operasi CRUD berhasil, tabel dimuat kembali dari database agar menampilkan kondisi terbaru.
9. Tombol Cancel digunakan untuk mengosongkan form, mengembalikan tombol ke mode Add, menyembunyikan tombol Delete, dan menghilangkan seleksi baris.

# Cara Menjalankan Program

1. Pastikan Java JDK dan IntelliJ IDEA telah tersedia.
2. Jalankan MySQL melalui XAMPP.
3. Buka phpMyAdmin dan buat database `db_people`.
4. Pilih database tersebut, lalu impor file `db_people.sql` untuk membuat tabel `person` beserta data awal.
5. Pastikan library MySQL Connector/J pada folder `lib` telah ditambahkan ke dependencies proyek.
6. Buka proyek di IntelliJ IDEA dan jalankan method `main()` pada kelas `PeopleMenu`.

**Catatan:** Konfigurasi koneksi pada kelas `Database` menggunakan host `localhost`, port `3306`, username `root`, dan password kosong. Sesuaikan konfigurasi tersebut jika pengaturan MySQL yang digunakan berbeda.

# Dokumentasi Program

Dokumentasi berikut menunjukkan tampilan aplikasi, operasi CRUD, dan validasi input.

### Screenshot

| Dokumentasi                    | File                                                                            |
| ------------------------------ | ------------------------------------------------------------------------------- |
| Tampilan awal aplikasi         | [01_Tampilan_Awal.png](Dokumentasi/Screenshot/01_Tampilan_Awal.png)             |
| Create berhasil                | [02_Add_Berhasil.png](Dokumentasi/Screenshot/02_Add_Berhasil.png)               |
| Error input kosong saat Create | [03_Error_Input_Kosong.png](Dokumentasi/Screenshot/03_Error_Input_Kosong.png)   |
| Error ID duplikat saat Create  | [04_Error_ID_Duplikat.png](Dokumentasi/Screenshot/04_Error_ID_Duplikat.png)     |
| Update berhasil                | [05_Update_Berhasil.png](Dokumentasi/Screenshot/05_Update_Berhasil.png)         |
| Error field kosong saat Update | [06_Error_Field_Kosong.png](Dokumentasi/Screenshot/06_Error_Field_Kosong.png)   |
| Error ID duplikat saat Update  | [07_Error_ID_Duplikat.png](Dokumentasi/Screenshot/07_Error_ID_Duplikat.png)     |
| Konfirmasi penghapusan         | [08_Konfirmasi_Delete.png](Dokumentasi/Screenshot/08_Konfirmasi_Delete.png)     |
| Delete berhasil                | [09_Delete_Berhasil.png](Dokumentasi/Screenshot/09_Delete_Berhasil.png)         |
| Error tahun lahir tidak valid  | [10_Error_Tahun_Invalid.png](Dokumentasi/Screenshot/10_Error_Tahun_Invalid.png) |

### Video

| Dokumentasi                                     | File                                                          |
| ----------------------------------------------- | ------------------------------------------------------------- |
| Tampilan awal aplikasi dan database MySQL       | [Video Tampilan Awal](Dokumentasi/Video/01_Tampilan_Awal.mp4) |
| Create: berhasil, input kosong, dan ID duplikat | [Video Create](Dokumentasi/Video/02_Create.mp4)               |
| Update: berhasil, input kosong, dan ID duplikat | [Video Update](Dokumentasi/Video/03_Update.mp4)               |
| Delete: konfirmasi No dan Yes                   | [Video Delete](Dokumentasi/Video/04_Delete.mp4)               |
| Reset form                                      | [Video Reset](Dokumentasi/Video/05_Reset.mp4)                 |

# Struktur Repository

```text
TP5DPBO2526C2/
├── PeopleData/
│   ├── Person.java
│   ├── Database.java
│   ├── PeopleMenu.java
│   └── PeopleMenu.form
├── lib/
│   └── mysql-connector-j-...jar
├── db_people.sql
├── Dokumentasi/
│   ├── Screenshot/
│   │   └── File screenshot dokumentasi
│   └── Video/
│       └── File video dokumentasi
└── README.md
```

# Kesimpulan

Aplikasi People Data merupakan program pengelolaan data berbasis Java Swing yang menerapkan konsep OOP melalui kelas `Person`, `PeopleMenu`, dan `Database`. Dengan menggunakan JDBC dan MySQL, program mendukung operasi CRUD dengan penyimpanan data yang persisten, validasi input, pemilihan kategori dan status keaktifan, serta konfirmasi sebelum penghapusan data.
