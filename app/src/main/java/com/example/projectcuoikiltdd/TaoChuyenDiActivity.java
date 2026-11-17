package com.example.projectcuoikiltdd;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaoChuyenDiActivity extends AppCompatActivity {

    private ImageView btnBack, imgAiCheckbox;
    private EditText edtTripName, edtDestination, edtTripNote, edtBudget;
    private TextView chipDalat, chipNinhBinh, chipPhuQuoc, chipHaGiang, chipSapa;
    private LinearLayout boxStartDate, boxEndDate, boxAiSuggest;
    private TextView tvStartDate, tvEndDate, tvDurationBadge;
    private Button btnSaveTrip;

    private int startDay = 15, endDay = 17;
    private boolean isAiEnabled = true; // Mặc định bật gợi ý AI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tao_chuyen_di);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXaView();
        xuLyChonDiemDen();
        xuLyChonNgay();
        xuLyNutBam();
    }

    private void anhXaView() {
        btnBack = findViewById(R.id.btn_back);
        edtTripName = findViewById(R.id.edt_trip_name);
        edtDestination = findViewById(R.id.edt_destination);
        edtTripNote = findViewById(R.id.edt_trip_note);
        edtBudget = findViewById(R.id.edt_budget);

        chipDalat = findViewById(R.id.chip_dalat);
        chipNinhBinh = findViewById(R.id.chip_ninhbinh);
        chipPhuQuoc = findViewById(R.id.chip_phuquoc);
        chipHaGiang = findViewById(R.id.chip_hagiang);
        chipSapa = findViewById(R.id.chip_sapa);

        boxStartDate = findViewById(R.id.box_start_date);
        boxEndDate = findViewById(R.id.box_end_date);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvEndDate = findViewById(R.id.tv_end_date);
        tvDurationBadge = findViewById(R.id.tv_duration_badge);

        boxAiSuggest = findViewById(R.id.box_ai_suggest);
        imgAiCheckbox = findViewById(R.id.img_ai_checkbox);
        btnSaveTrip = findViewById(R.id.btn_save_trip);
    }

    private void xuLyChonDiemDen() {
        chipDalat.setOnClickListener(v -> chonChip(chipDalat, "Đà Lạt", "Đà Lạt, Lâm Đồng"));
        chipNinhBinh.setOnClickListener(v -> chonChip(chipNinhBinh, "Ninh Bình", "Tràng An, Ninh Bình"));
        chipPhuQuoc.setOnClickListener(v -> chonChip(chipPhuQuoc, "Phú Quốc", "Đảo Phú Quốc, Kiên Giang"));
        chipHaGiang.setOnClickListener(v -> chonChip(chipHaGiang, "Hà Giang", "Đồng Văn, Hà Giang"));
        chipSapa.setOnClickListener(v -> chonChip(chipSapa, "Sa Pa", "Thị xã Sa Pa, Lào Cai"));
    }

    private void chonChip(TextView selectedChip, String shortName, String fullLocation) {
        TextView[] allChips = {chipDalat, chipNinhBinh, chipPhuQuoc, chipHaGiang, chipSapa};
        String[] labels = {"Đà Lạt", "Ninh Bình", "Phú Quốc", "Hà Giang", "Sa Pa"};

        for (int i = 0; i < allChips.length; i++) {
            allChips[i].setBackgroundResource(R.drawable.bg_chip_unselected);
            allChips[i].setTextColor(Color.parseColor("#424242"));
            allChips[i].setText(labels[i]);
        }

        selectedChip.setBackgroundResource(R.drawable.bg_chip_selected);
        selectedChip.setTextColor(Color.WHITE);
        selectedChip.setText("✓ " + shortName);
        edtDestination.setText(fullLocation);
    }

    private void xuLyChonNgay() {
        boxStartDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                startDay = dayOfMonth;
                tvStartDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                capNhatSoNgayDem();
            }, 2026, 10, startDay);
            dialog.show();
        });

        boxEndDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                endDay = dayOfMonth;
                tvEndDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                capNhatSoNgayDem();
            }, 2026, 10, endDay);
            dialog.show();
        });
    }

    private void capNhatSoNgayDem() {
        int days = Math.max(1, endDay - startDay + 1);
        int nights = Math.max(0, days - 1);
        tvDurationBadge.setText(days + " ngày " + nights + " đêm");
    }

    private void xuLyNutBam() {
        btnBack.setOnClickListener(v -> finish());

        // Bấm bật/tắt Trợ lý AI Việt Hành
        boxAiSuggest.setOnClickListener(v -> {
            isAiEnabled = !isAiEnabled;
            if (isAiEnabled) {
                imgAiCheckbox.setImageResource(R.drawable.bg_checkbox_checked);
            } else {
                imgAiCheckbox.setImageResource(R.drawable.bg_checkbox_unchecked);
            }
        });

        btnSaveTrip.setOnClickListener(v -> {
            String tripName = edtTripName.getText().toString().trim();
            if (tripName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên chuyến đi!", Toast.LENGTH_SHORT).show();
                return;
            }
            String aiStatus = isAiEnabled ? " (Có gợi ý AI)" : "";
            Toast.makeText(this, "Đã tạo chuyến đi: " + tripName + aiStatus, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}