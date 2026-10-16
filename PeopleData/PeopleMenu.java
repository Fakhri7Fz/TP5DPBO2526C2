
// Mengimpor kelas untuk menangani koneksi dan interaksi dengan database
import java.sql.ResultSet;

// Mengimpor kelas untuk menangani kesalahan SQL
import java.sql.SQLException;

// Mengimpor komponen antarmuka Swing, seperti JFrame, JButton, JTable, dan JOptionPane
import javax.swing.*;

// Mengimpor model data yang digunakan untuk mengisi JTable
import javax.swing.table.DefaultTableModel;

// Mengimpor kelas AWT; di file ini Font digunakan untuk mengatur tampilan judul
import java.awt.*;

// Mengimpor informasi tentang aksi yang terjadi, misalnya saat tombol diklik
import java.awt.event.ActionEvent;

// Mengimpor listener untuk menangani aksi pada tombol
import java.awt.event.ActionListener;

// Mengimpor kelas pembantu untuk menangani interaksi mouse
import java.awt.event.MouseAdapter;

// Mengimpor informasi tentang kejadian mouse, seperti klik pada baris tabel
import java.awt.event.MouseEvent;

// Menyediakan antarmuka untuk melihat dan mengelola data sivitas akademika.
public class PeopleMenu extends JFrame {
    // Panel utama yang menampung seluruh komponen tampilan
    private JPanel mainPanel;

    // Label judul aplikasi
    private JLabel titleLabel;

    // Field untuk memasukkan ID, nama, dan tahun lahir
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;

    // Pilihan kategori orang
    private JComboBox<String> kategoriComboBox;

    // Panel dan tombol pilihan status keaktifan
    private JPanel statusRadioPanel;
    private JRadioButton aktifRadioButton;
    private JRadioButton nonAktifRadioButton;

    // Mengelompokkan tombol status agar hanya satu yang dapat dipilih
    private ButtonGroup statusButtonGroup;

    // Tombol untuk menambah atau memperbarui data
    private JButton addUpdateButton;

    // Tombol untuk membatalkan atau mengosongkan form
    private JButton cancelButton;

    // Tombol untuk menghapus data yang dipilih
    private JButton deleteButton;

    // Tabel untuk menampilkan daftar orang
    private JTable peopleTable;

    // Panel gulir untuk tabel
    private JScrollPane tableScrollPane;

    // Objek untuk mengakses database
    private Database database;

    // Indeks baris yang dipilih
    private int selectedIndex = -1;

    public PeopleMenu() {
        // Inisialisasi koneksi database
        database = new Database();

        // Setup awal pilihan Kategori (Sivitas Akademika)
        String[] kategoriData = { "--- Pilih Kategori ---", "S1 Reguler", "S2 Magister", "D3 Vokasi", "Dosen",
                "Tenaga Kependidikan" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Grouping Radio Button untuk Status Keaktifan (Bonus Component)
        statusButtonGroup = new ButtonGroup();
        statusButtonGroup.add(aktifRadioButton);
        statusButtonGroup.add(nonAktifRadioButton);
        aktifRadioButton.setSelected(true); // Default

        // Set model tabel berdasarkan data dari database
        peopleTable.setModel(setTable());

        // Styling Title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Sembunyikan button delete di awal
        deleteButton.setVisible(false);

        // Listener: Tombol Add / Update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aksi tombol mengikuti mode form: tambah saat belum memilih baris,
                // atau perbarui data saat sebuah baris sedang dipilih.
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Listener: Tombol Delete (dengan Pop-up Konfirmasi)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // Listener: Tombol Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Listener: Klik Baris JTable
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ambil indeks baris tabel yang dipilih
                selectedIndex = peopleTable.getSelectedRow();

                if (selectedIndex != -1) {
                    // Ambil nilai dari tabel
                    String curId = peopleTable.getModel().getValueAt(selectedIndex, 1).toString();
                    String curNama = peopleTable.getModel().getValueAt(selectedIndex, 2).toString();
                    String curTahun = peopleTable.getModel().getValueAt(selectedIndex, 3).toString();
                    String curKategori = peopleTable.getModel().getValueAt(selectedIndex, 4).toString();
                    String curStatus = peopleTable.getModel().getValueAt(selectedIndex, 5).toString();

                    // Isi form input secara otomatis
                    idField.setText(curId);
                    namaField.setText(curNama);
                    tahunLahirField.setText(curTahun);
                    kategoriComboBox.setSelectedItem(curKategori);

                    if (curStatus.equalsIgnoreCase("Aktif")) {
                        aktifRadioButton.setSelected(true);
                    } else {
                        nonAktifRadioButton.setSelected(true);
                    }

                    // Ubah tombol "Add" menjadi "Update" dan tampilkan "Delete"
                    addUpdateButton.setText("Update");
                    deleteButton.setVisible(true);
                }
            }
        });
    }

    // Membuat model tabel dan mengisinya dengan data dari database.
    public final DefaultTableModel setTable() {
        // Tentukan kolom tabel
        Object[] cols = {
                "No", "ID / NIM / NIP", "Nama",
                "Tahun Lahir", "Kategori", "Status Keaktifan"
        };

        // Buat model tabel kosong
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        // Ambil data dari database
        try {
            ResultSet resultSet = database.selectQuery(
                    "SELECT * FROM person");

            int i = 1;

            while (resultSet.next()) {
                Object[] row = {
                        i,
                        resultSet.getString("id"),
                        resultSet.getString("nama"),
                        resultSet.getInt("thn_lahir"),
                        resultSet.getString("kategori"),
                        resultSet.getString("status_keaktifan")
                };

                tmp.addRow(row);
                i++;
            }

            resultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal mengambil data: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return tmp;
    }

    // Fungsi Tambah Data
    public void insertData() {
        try {
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String tahunStr = tahunLahirField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String status = aktifRadioButton.isSelected() ? "Aktif" : "Non-Aktif";

            // Validasi input kosong
            if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()
                    || kategori.equals("--- Pilih Kategori ---")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Semua field harus diisi dengan benar!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int tahunLahir = Integer.parseInt(tahunStr);

            // Validasi tahun lahir
            if (tahunLahir < 1900 || tahunLahir > 2026) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tahun lahir harus antara 1900 dan 2026!",
                        "Input Tidak Valid",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Periksa apakah ID sudah digunakan
            ResultSet resultSet = database.selectQuery(
                    "SELECT id FROM person WHERE id = '" + id + "'");

            boolean idExists = resultSet.next();
            resultSet.close();

            if (idExists) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID sudah digunakan! Silakan gunakan ID lain.",
                        "ID Duplikat",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simpan data ke database
            String sqlQuery = "INSERT INTO person "
                    + "(id, nama, thn_lahir, kategori, status_keaktifan) VALUES ('"
                    + id + "', '"
                    + nama + "', "
                    + tahunLahir + ", '"
                    + kategori + "', '"
                    + status + "')";

            database.insertUpdateDeleteQuery(sqlQuery);

            // Perbarui tabel dan bersihkan form
            peopleTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil ditambahkan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tahun lahir harus berupa angka!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memeriksa ID: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal menambahkan data: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi Update Data
    public void updateData() {
        try {
            // Pastikan ada data yang sedang dipilih
            if (selectedIndex == -1) {
                return;
            }

            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String tahunStr = tahunLahirField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String status = aktifRadioButton.isSelected() ? "Aktif" : "Non-Aktif";

            // Validasi input kosong
            if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()
                    || kategori.equals("--- Pilih Kategori ---")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Semua field harus diisi dengan benar!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int tahunLahir = Integer.parseInt(tahunStr);

            // Validasi tahun lahir
            if (tahunLahir < 1900 || tahunLahir > 2026) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tahun lahir harus antara 1900 dan 2026!",
                        "Input Tidak Valid",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Ambil ID asli dari baris yang sedang diedit
            String oldId = peopleTable.getModel()
                    .getValueAt(selectedIndex, 1).toString();

            // Periksa apakah ID baru sudah digunakan oleh data lain
            ResultSet resultSet = database.selectQuery(
                    "SELECT id FROM person WHERE id = '" + id
                            + "' AND id <> '" + oldId + "'");

            boolean idExists = resultSet.next();
            resultSet.close();

            if (idExists) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID sudah digunakan! Silakan gunakan ID lain.",
                        "ID Duplikat",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Perbarui data di database berdasarkan ID asli
            String sqlQuery = "UPDATE person SET "
                    + "id = '" + id + "', "
                    + "nama = '" + nama + "', "
                    + "thn_lahir = " + tahunLahir + ", "
                    + "kategori = '" + kategori + "', "
                    + "status_keaktifan = '" + status + "' "
                    + "WHERE id = '" + oldId + "'";

            database.insertUpdateDeleteQuery(sqlQuery);

            // Perbarui tabel dan kembalikan form ke kondisi awal
            peopleTable.setModel(setTable());
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil diperbarui!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tahun lahir harus berupa angka!",
                    "Error Validasi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memeriksa ID: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memperbarui data: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi Delete Data dengan Pop-up Konfirmasi
    public void deleteData() {
        if (selectedIndex != -1) {
            // Ambil ID dan nama langsung dari baris tabel yang dipilih
            String id = peopleTable.getModel()
                    .getValueAt(selectedIndex, 1).toString();
            String nama = peopleTable.getModel()
                    .getValueAt(selectedIndex, 2).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin menghapus data " + nama + "?",
                    "Konfirmasi Hapus Data",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Hapus data dari database berdasarkan ID
                    String sqlQuery = "DELETE FROM person WHERE id = '" + id + "'";
                    database.insertUpdateDeleteQuery(sqlQuery);

                    // Perbarui tabel dan kembalikan form ke kondisi awal
                    peopleTable.setModel(setTable());
                    clearForm();

                    JOptionPane.showMessageDialog(
                            this,
                            "Data berhasil dihapus!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Gagal menghapus data: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Reset Form Input ke Kondisi Awal
    public void clearForm() {
        // Kosongkan seluruh field input
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");

        // Kembalikan kategori dan status ke pilihan default
        kategoriComboBox.setSelectedIndex(0);
        aktifRadioButton.setSelected(true);

        // Kembalikan tombol ke mode tambah dan sembunyikan tombol Delete
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);

        // Tandai bahwa tidak ada baris yang sedang diedit
        selectedIndex = -1;

        // Hilangkan seleksi baris pada tabel
        peopleTable.clearSelection();
    }

    // Main Method
    public static void main(String[] args) {
        PeopleMenu frame = new PeopleMenu();
        // Atur jendela utama sebelum menampilkannya ke pengguna.
        frame.setTitle("Sistem Data Sivitas Akademika - PeopleData");
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(frame.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}