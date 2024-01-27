package com.example.sispak_kel_9;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sispak_kel_9.ml.Sazara;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class deteksi extends AppCompatActivity {
    ImageView imageView;
    ImageButton button1, button2;
    int imageSize = 128;
    TextView confidence, solusi, result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi);
        ImageButton button = (ImageButton) findViewById(R.id.button2);
        this.button2 = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.imageclassifier.MainActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent cameraIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                deteksi.this.startActivityForResult(cameraIntent, 1);
            }
        });

        result = findViewById(R.id.hasilprediksi);
        confidence = findViewById(R.id.hasilklasifikasi);
        solusi = findViewById(R.id.solusi);
        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deteksi.this.checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    deteksi.this.startActivityForResult(cameraIntent, 3);
                    return;
                }
                deteksi.this.requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
            }
        });
    }
    public void classifyImage(Bitmap image) {
        try {
            Sazara model = Sazara.newInstance(getApplicationContext());

            int imageSize = 128; // Sesuaikan dengan ukuran input model

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(imageSize * imageSize * 3 * 4); // 4 bytes per float
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; ++i) {
                for (int j = 0; j < imageSize; ++j) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * 0.003921569f);
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * 0.003921569f);
                    byteBuffer.putFloat((val & 0xFF) * 0.003921569f);
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            Sazara.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();

            int maxPos = 0;
            float maxConfidence = 0.0f;
            for (int i = 0; i < confidences.length; ++i) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Anthracnose", "Downy_Mildew", "Mosaic_Virus"};

            result.setText(classes[maxPos]);
            if (classes[maxPos].equals("Anthracnose")) {
                String more = "Selengkapnya";
                String solutionText = "Anthracnose pada tanaman semangka dapat dikelola dengan langkah-langkah berikut:\n" +
                        "\n" +
                        "1. Rotasi Tanaman\n" +
                        "\n" +
                        "Lakukan rotasi tanaman untuk mencegah penumpukan patogen di tanah.\n" +
                        "2. Pilih Varietas Tahan\n" +
                        "\n" +
                        "Pilih varietas semangka yang tahan terhadap anthracnose.\n" +
                        "3. Penyusunan Tanaman yang Tepat\n" +
                        "\n" +
                        "Pastikan penyusunan tanaman mendukung sirkulasi udara yang baik.\n" +
                        "4. Pemangkasan\n" +
                        "\n" +
                        "Rutin pemangkasan bagian tanaman terinfeksi untuk menghentikan penyebaran.\n" +
                        "5. Aplikasi Fungisida\n" +
                        "\n" +
                        "Gunakan fungisida tembaga untuk kontrol anthracnose, ikuti petunjuk dengan benar.\n" +
                        "6. Manajemen Air\n" +
                        "\n" +
                        "Terapkan manajemen air yang baik, hindari penyiraman berlebihan.\n" +
                        "7. Pemulsaan\n" +
                        "\n" +
                        "Gunakan pemulsa organik di sekitar tanaman untuk menjaga kelembaban.\n" +
                        "8. Sanitasi\n" +
                        "\n" +
                        "Pertahankan kebun bersih, buang material terinfeksi untuk mengurangi sumber penyakit.\n" +
                        "9. Pemberian Bahan Tanah\n" +
                        "\n" +
                        "Pastikan tanah memiliki drainase baik, tambahkan bahan organik seperti kompos.\n" +
                        "10. Pemantauan Kondisi Cuaca\n" +
                        "\n" +
                        "Waspada selama periode kelembapan tinggi dan hujan, ambil langkah pencegahan.\n" +
                        "Kombinasi strategi ini lebih efektif, dengan pemantauan rutin dan tindakan cepat sangat penting. Konsultasikan dengan ahli pertanian setempat jika infeksi parah.";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n " + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Anthracnose",
                                "Anthracnose adalah penyakit jamur yang umumnya menyerang tanaman semangka. Untuk mengelola dan mencegah anthracnose pada tanaman semangka, Anda dapat mempertimbangkan solusi berikut:\n" +
                                        "" +
                                        "1. Rotasi Tanaman" +
                                        "Rotasi tanaman secara teratur untuk mencegah penumpukan patogen dalam tanah. Hindari menanam semangka atau tanaman rentan lainnya di area yang sama selama musim tanam berturut-turut.\n" +
                                        "" +
                                        "2. Varietas Tahan" +
                                        "Pilih varietas semangka yang tahan terhadap anthracnose. Varietas tahan dapat secara signifikan mengurangi risiko infeksi.\n" +
                                        "" +
                                        "3. Penyusunan Tanaman yang Tepat" +
                                        "Pastikan penyusunan tanaman yang tepat untuk mempromosikan sirkulasi udara yang baik. Ini membantu mengurangi kelembapan di sekitar tanaman, membuatnya kurang cocok untuk pertumbuhan jamur.\n" +
                                        "" +
                                        "4. Pemangkasan" +
                                        "Rutin memangkas dan menghilangkan bagian tanaman yang terinfeksi. Ini termasuk penghapusan daun, batang, dan buah yang terinfeksi. Buang bahan tanaman yang terinfeksi jauh dari kebun untuk mencegah penyebaran lebih lanjut.\n" +
                                        "" +
                                        "5. Aplikasi Fungisida" +
                                        "Gunakan fungisida yang efektif melawan anthracnose. Fungisida berbasis tembaga umumnya digunakan untuk mengendalikan penyakit jamur pada tanaman semangka. Ikuti petunjuk pada label produk untuk aplikasi yang benar.\n" +
                                        "" +
                                        "6. Manajemen Air" +
                                        "Praktikkan manajemen air yang baik untuk menghindari penyiraman berlebih. Siram pada bagian dasar tanaman, hindari membasahi daun. Penyiraman pada pagi hari memungkinkan daun cepat kering dan mengurangi kemungkinan pertumbuhan jamur.\n" +
                                        "" +
                                        "7. Pemulsaan" +
                                        "Aplikasikan lapisan pemulsa organik di sekitar pangkal tanaman. Pemulsa membantu dalam retensi kelembaban, mengurangi percikan tanah ke daun, dan dapat menciptakan penghalang terhadap spora jamur.\n" +
                                        "" +
                                        "8. Sanitasi" +
                                        "Pertahankan kebun bersih dan bebas dari sisa tanaman. Hapus dan hancurkan material tanaman yang terinfeksi segera. Ini mengurangi sumber inokulum penyakit.\n" +
                                        "" +
                                        "9. Pemberian Bahan Tanah" +
                                        "Pastikan tanah memiliki drainase yang baik. Menambahkan bahan organik, seperti kompos, dapat meningkatkan struktur tanah dan drainase.\n" +
                                        "" +
                                        "10. Pemantauan Kondisi Cuaca" +
                                        "Tetap waspada selama periode kelembapan tinggi dan hujan yang sering, karena kondisi ini mendukung perkembangan dan penyebaran anthracnose. Ambil langkah pencegahan selama periode tersebut.\n" +
                                        "" +
                                        "Ingatlah bahwa kombinasi dari strategi-strategi ini seringkali lebih efektif daripada mengandalkan satu metode. Pemantauan teratur tanaman Anda dan tindakan cepat ketika gejala muncul sangat penting untuk mengelola anthracnose dan menjaga tanaman semangka tetap sehat. Jika infeksi sudah parah, konsultasikan dengan layanan perluasan pertanian setempat atau spesialis penyakit tanaman untuk bimbingan tambahan.");
                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            }else if(classes[maxPos].equals("Downy_Mildew")){
                String more = "Selengkapnya";
                String solutionText = "Penanganan penyakit downy mildew pada tanaman semangka melibatkan langkah-langkah berikut:\n" +
                        "1. Rotasi Tanaman\n" +
                        "- Lakukan rotasi tanaman untuk mengurangi penyebaran patogen.\n" +
                        "2. Pilih Varietas Tahan\n" +
                        "- Pilih varietas semangka yang tahan terhadap downy mildew.\n" +
                        "3. Pengelolaan Air\n" +
                        "- Lakukan penyiraman pada pagi hari dan hindari kelembaban tinggi.\n" +
                        "4. Perawatan Tanah\n" +
                        "- Jaga kebersihan area sekitar tanaman, hindari penumpukan sisa tanaman terinfeksi.\n" +
                        "5. Aplikasi Fungisida\n" +
                        "- Gunakan fungisida yang disetujui dengan memperhatikan petunjuk label.\n" +
                        "6. Pemantauan Rutin:\n" +
                        "- Monitor tanaman secara rutin dan tanggapi gejala downy mildew dengan cepat.\n" +
                        "7. Praktik Budidaya yang Baik:\n" +
                        "- Tanam dengan jarak cukup untuk sirkulasi udara dan kurangi kelembaban di daun.\n" +
                        "8. Kontrol Serangga Vektor:\n" +
                        "- Kendalikan populasi serangga yang dapat menyebarkan spora downy mildew.\n" +
                        "9. Pemakaian Mulsa:\n" +
                        "- Gunakan mulsa di sekitar tanaman untuk menjaga kelembaban dan mengurangi kontak langsung dengan air hujan.\n" +
                        "10. Pemangkasan:\n" +
                        "- Lakukan pemangkasan secara teratur untuk meningkatkan sirkulasi udara.\n" +
                        "\n" +
                        "Penting untuk menggabungkan beberapa metode pengendalian dan menjaga kebersihan menyeluruh. Konsultasikan dengan ahli pertanian atau petani lokal untuk rekomendasi yang sesuai dengan kondisi tempat Anda.";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "" + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Downy Mildew",
                                "Penyakit downy mildew pada tanaman semangka disebabkan oleh jamur Pseudoperonospora cubensis. Berikut adalah beberapa solusi yang dapat diterapkan untuk mengatasi atau mencegah penyakit downy mildew:\n\n" +

                                        "1. Rotasi Tanaman:\n" +
                                        "   - Lakukan rotasi tanaman di lapangan. Hindari menanam semangka atau tanaman yang rentan terhadap downy mildew secara berurutan di lokasi yang sama setiap tahun. Rotasi tanaman dapat membantu mengurangi penyebaran patogen.\n" +

                                        "2. Pemilihan Varietas Unggul:\n" +
                                        "   - Pilih varietas semangka yang tahan terhadap downy mildew jika tersedia. Beberapa varietas telah dikembangkan dengan ketahanan terhadap penyakit ini.\n" +

                                        "3. Pengelolaan Air:\n" +
                                        "   - Pastikan penyiraman tanaman dilakukan pada pagi hari agar tanaman memiliki waktu yang cukup untuk mengering sebelum malam tiba. Hindari penyiraman berlebihan dan pertahankan kelembaban rendah di sekitar tanaman.\n" +

                                        "4. Perawatan Tanah:\n" +
                                        "   - Jaga kebersihan area di sekitar tanaman semangka. Hindari penumpukan sisa tanaman atau daun yang terinfeksi, karena jamur dapat bertahan hidup dalam sisa tanaman tersebut.\n" +

                                        "5. Aplikasi Fungisida:\n" +
                                        "   - Gunakan fungisida yang disetujui untuk mengendalikan downy mildew. Pemilihan fungisida harus dilakukan sesuai dengan petunjuk label dan memperhatikan masa panen yang aman.\n" +

                                        "6. Pemantauan Rutin:\n" +
                                        "   - Lakukan pemantauan rutin terhadap tanaman semangka. Jika terdapat gejala downy mildew, segera ambil tindakan dengan menggunakan fungisida atau metode pengendalian lainnya.\n" +

                                        "7. Praktik Budidaya yang Baik:\n" +
                                        "   - Terapkan praktik budidaya yang baik, termasuk penanaman dengan jarak yang cukup antar tanaman untuk meningkatkan sirkulasi udara dan mengurangi kelembaban di daun.\n" +

                                        "8. Kontrol Serangga Vektor:\n" +
                                        "   - Beberapa serangga dapat membantu menyebarkan spora downy mildew. Kontrol populasi serangga vektor dapat membantu mengurangi penyebaran penyakit.\n" +

                                        "9. Pemakaian Mulsa:\n" +
                                        "   - Gunakan mulsa di sekitar tanaman untuk membantu menjaga kelembaban tanah dan mengurangi kontak langsung antara air hujan dan tanaman.\n" +

                                        "10. Pemangkasan:\n" +
                                        "    - Lakukan pemangkasan secara teratur untuk meningkatkan sirkulasi udara di dalam tanaman semangka.\n" +

                                        "Penting untuk diingat bahwa pendekatan terbaik adalah menggabungkan beberapa metode pengendalian dan menjaga kebersihan secara menyeluruh. Konsultasikan dengan ahli pertanian atau petani lokal untuk mendapatkan rekomendasi yang sesuai dengan kondisi tempat Anda."
                        );

                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            }else if(classes[maxPos].equals("Mosaic_Virus")){
                String more = "Selengkapnya";
                String solutionText = "Penanganan penyakit mosaic virus pada tanaman semangka:\n" +
                        "\n" +
                        "1. Pilih Varitas Tahan Virus\n" +
                        "Pilih varietas semangka yang tahan terhadap virus mosaic.\n" +
                        "\n" +
                        "2. Kendalikan Serangga Penyebab Virus\n" +
                        "Gunakan insektisida untuk mengendalikan serangga pengusung virus.\n" +
                        "\n" +
                        "3. Pengelolaan Tanaman\n" +
                        "\n" +
                        "Tanam dengan jarak yang memadai.\n" +
                        "Praktikkan sanitasi dan gunakan peralatan bersih.\n" +
                        "4. Kendalikan Gulma\n" +
                        "Bersihkan area dari gulma, yang dapat menjadi sumber virus.\n" +
                        "\n" +
                        "5. Irigasi yang Baik\n" +
                        "Pastikan irigasi dilakukan dengan baik.\n" +
                        "\n" +
                        "6. Monitoring Rutin\n" +
                        "Pemantauan teratur untuk deteksi dini.\n" +
                        "\n" +
                        "7. Konsultasikan dengan Ahli Pertanian\n" +
                        "Jika gejala persisten, konsultasikan dengan ahli pertanian setempat.";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n " + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Mosaic Virus", " Penanganan penyakit mosaic virus pada tanaman semangka dapat melibatkan beberapa tindakan. Namun, perlu diingat bahwa tidak ada pengobatan yang spesifik untuk virus, dan langkah-langkah ini lebih bersifat pencegahan dan pengelolaan. Berikut beberapa solusi yang dapat Anda pertimbangkan:\n" +
                                "\n" +
                                "1. Pemilihan Benih yang Tahan Terhadap Virus\n" +
                                "Pilih varietas tanaman semangka yang tahan terhadap virus mosaic jika tersedia. Beberapa varietas mungkin memiliki ketahanan alami terhadap serangan virus tertentu.\n" +
                                "\n" +
                                "2. Kendalikan Serangga Pengusung Virus\n" +
                                "Serangga seperti kutu daun atau trips dapat menjadi vektor untuk menyebarkan virus mosaic. Pertimbangkan untuk menggunakan insektisida yang sesuai untuk mengendalikan serangga tersebut.\n" +
                                "\n" +
                                "3. Pengelolaan Tanaman\n" +
                                "\n" +
                                "Pastikan tanaman ditanam pada jarak yang memadai untuk mengurangi penularan.\n" +
                                "Praktik sanitasi yang baik, seperti menghilangkan tanaman yang terinfeksi atau daun yang terkena virus.\n" +
                                "Gunakan peralatan tani yang bersih untuk mencegah penularan virus melalui alat-alat tani.\n" +
                                "4. Pengendalian Gulma\n" +
                                "Bersihkan area sekitar tanaman dari gulma, karena beberapa gulma dapat bertindak sebagai reservoir virus.\n" +
                                "\n" +
                                "5. Irigasi yang Baik\n" +
                                "Pastikan irigasi dilakukan dengan baik. Kelembaban yang konsisten dapat membantu tanaman semangka menjadi lebih tahan terhadap infeksi virus.\n" +
                                "\n" +
                                "6. Monitoring Rutin\n" +
                                "Lakukan pemantauan rutin terhadap tanaman semangka untuk mendeteksi gejala mosaic virus sesegera mungkin. Tanaman yang terinfeksi sebaiknya diisolasi atau dihilangkan untuk mencegah penularan lebih lanjut.\n" +
                                "\n" +
                                "7. Konsultasikan dengan Ahli Pertanian\n" +
                                "Jika gejala tetap muncul atau menjadi parah, konsultasikan dengan ahli pertanian atau penyuluh pertanian setempat. Mereka dapat memberikan panduan lebih lanjut dan saran yang sesuai dengan kondisi pertanian di daerah Anda.\n" +
                                "\n" +
                                "Perlu diingat bahwa mengelola penyakit viral pada tanaman melibatkan pendekatan holistik. Kombinasi dari berbagai strategi dapat membantu meningkatkan ketahanan tanaman dan mengurangi dampak penyakit.");

                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            }

            String s = "";
            for (int i = 0; i < classes.length; ++i) {
                s = s + String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100.0f);
            }
            confidence.setText(s);

            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showExplanationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) { // Handle camera result
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                Bitmap image2 = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                this.imageView.setImageBitmap(image2);
                int i = this.imageSize;
                classifyImage(Bitmap.createScaledBitmap(image2, i, i, false));
            } else if (requestCode == 1) { // Handle gallery result
                Uri dat = data.getData();
                if (dat != null) {
                    Bitmap image3 = null;
                    try {
                        image3 = MediaStore.Images.Media.getBitmap(getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (image3 != null) {
                        this.imageView.setImageBitmap(image3);
                        int i2 = this.imageSize;
                        classifyImage(Bitmap.createScaledBitmap(image3, i2, i2, false));
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Intent backIntent = new Intent(this, deteksi.class);
            startActivity(backIntent);
            finish(); // Optional: finish the current activity
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
