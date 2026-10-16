// Menyimpan data seseorang yang digunakan oleh aplikasi.
public class Person {
    private String id; // Atribut ID unik untuk setiap orang
    private String nama; // Atribut nama orang
    private int tahunLahir; // Atribut tahun lahir orang
    private String kategori; // Atribut kategori orang (misalnya: "Mahasiswa", "Dosen", dll.)
    private String statusKeaktifan; // Atribut status keaktifan orang (misalnya: "Aktif", "Tidak Aktif")

    // Constructor kosong
    public Person() {
    }

    // Constructor dengan parameter
    public Person(String id, String nama, int tahunLahir, String kategori, String statusKeaktifan) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.statusKeaktifan = statusKeaktifan;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTahunLahir() {
        return tahunLahir;
    }

    public void setTahunLahir(int tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatusKeaktifan() {
        return statusKeaktifan;
    }

    public void setStatusKeaktifan(String statusKeaktifan) {
        this.statusKeaktifan = statusKeaktifan;
    }
}