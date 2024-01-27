package com.example.sispak_kel_9;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
public class daftar_penyakit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyakit);
        ImageView anthracnoseImageView = findViewById(R.id.anthracnose);
        anthracnoseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Anthracnose", "Penyakit jamur pada tanaman semangka seperti Anthracnose disebabkan oleh Colletotrichum spp. Beberapa gejala meliputi:\n\n" +
                        "- Bercak pada Daun, Batang, dan Buah: Muncul bercak gelap, terutama di bagian pinggiran.\n" +
                        "- Peningkatan Penyebaran pada Cuaca Lembab.\n\n" +
                        "Pencegahan melibatkan praktik budidaya baik sanitasi, pengendalian kelembaban, dan penggunaan fungisida. Pilih varietas tahan penyakit, lakukan monitoring dan tindakan pencegahan yang cepat.");

            }

        });
        ImageView downyMildewImageView = findViewById(R.id.downymildew);
        downyMildewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Downy Mildew", "Downy Mildew adalah penyakit jamur pada tanaman semangka yang disebabkan oleh Pseudoperonospora cubensis. Gejala meliputi:\n\n" +
                        "1. Bercak Kuning atau Keabu-abuan pada Daun:\n" +
                        "- Daun mengalami perubahan warna menjadi kuning atau keabu-abuan.\n" +
                        "Bercak biasanya muncul terlebih dahulu pada bagian atas daun.\n\n" +
                        "2. Bercak Menguning Membesar:\n" +
                        "- Bercak kuning dapat berkembang dan membesar seiring waktu.\n\n" +
                        "3. Bulu-bulu Putih seperti Kapas pada Bagian Bawah Daun:\n" +
                        "- Pada kondisi lembab, bagian bawah daun dapat memiliki bulu putih yang menyerupai kapas.\n" +
                        "Bulu-bulu ini adalah sporangia jamur yang menyebabkan penyebaran penyakit.\n\n" +
                        "4. Daun Layu dan Kering:\n" +
                        "- Daun terinfeksi dapat mengalami layu dan mengering.\n" +
                        "Kejadian parah dapat menyebabkan kehilangan hijau pada tanaman.\n\n" +
                        "5. Penurunan Hasil:\n" +
                        "- Menyebabkan penurunan hasil karena gangguan pada proses fotosintesis.\n\n" +
                        "Pencegahan dan Pengendalian:\n" +
                        "- Pilih varietas tahan.\n" +
                        "- Lakukan rotasi tanaman.\n" +
                        "- Terapkan pengelolaan sanitasi.\n" +
                        "- Kontrol kelembaban dan berikan ruang di antara tanaman.\n" +
                        "- Gunakan fungisida yang direkomendasikan jika penyakit muncul.");

            }
        });

        ImageView mosaicVirusImageView = findViewById(R.id.mosaicvirus);
        mosaicVirusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Mosaic Virus", "Penyakit ini ditandai oleh bercak-bercak kecil atau motif mozaik pada daun tanaman semangka.\n\n" +
                        "1. Bercak Motif Mozaik pada Daun:\n" +
                        "- Muncul bercak kecil atau motif mozaik pada daun tanaman semangka.\n" +
                        "- Bercak dapat memiliki pola tidak teratur dan berbeda dari warna normal daun.\n\n" +
                        "2. Deformitas pada Daun dan Buah:\n" +
                        "- Daun terinfeksi dapat mengalami deformitas atau perubahan bentuk.\n" +
                        "- Buah dari tanaman yang terinfeksi juga bisa menunjukkan gejala deformitas atau warna tidak normal.\n\n" +
                        "3. Penurunan Produksi:\n" +
                        "- Tanaman terinfeksi dapat mengalami penurunan produksi buah.\n" +
                        "- Buah yang dihasilkan mungkin lebih kecil atau kurang berkualitas.\n\n" +
                        "4. Penyakit Menyebar melalui Serangga:\n" +
                        "- Penyakit ini sering menyebar melalui serangga yang menginfeksi tanaman saat menyedot sari tanaman yang terinfeksi.\n\n" +
                        "5. Tidak Ada Pengobatan yang Spesifik:\n" +
                        "- Tidak ada pengobatan khusus untuk Mosaic Virus.\n" +
                        "- Pencegahan melalui pengelolaan serangga vektor dan tanaman yang sehat menjadi kunci dalam pengendalian penyakit ini.");

            }
        });
    }
    private void showExplanationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User mengklik tombol OK.
                        dialog.dismiss();
                    }
                })
                .show();
    }
}