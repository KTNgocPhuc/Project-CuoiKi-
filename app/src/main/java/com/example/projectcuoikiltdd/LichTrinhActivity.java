package com.example.projectcuoikiltdd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LichTrinhActivity extends AppCompatActivity {

    private TextView tvMonthYear, btnPrevMonth, btnNextMonth;
    private TextView tvDay15, tvDay16, tvDay17, tvDay18;
    private TextView tvLegendSelected, tvViewingDate, tvFilterLabel, tvViewDetail;
    private LinearLayout btnFilterTrips;
    private CardView cardTripDalat;
    private Button btnTaoChuyenDi;
    private BottomNavigationView bottomNavigation;

    private int currentMonth = 11;
    private int currentYear = 2026;
    private int selectedDay = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lich_trinh); // Đổi thành activity_lich_trinh_2 nếu file XML của bạn tên vậy

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        anhXaView();
        xuLySuKienLich();
        xuLySuKienChuyenDi();
        xuLyBottomNavigation();
    }

    private void anhXaView() {
        tvMonthYear = findViewById(R.id.tv_month_year);
        btnPrevMonth = findViewById(R.id.btn_prev_month);
        btnNextMonth = findViewById(R.id.btn_next_month);

        tvDay15 = findViewById(R.id.tv_day_15);
        tvDay16 = findViewById(R.id.tv_day_16);
        tvDay17 = findViewById(R.id.tv_day_17);
        tvDay18 = findViewById(R.id.tv_day_18);

        tvLegendSelected = findViewById(R.id.tv_legend_selected);
        tvViewingDate = findViewById(R.id.tv_viewing_date);
        btnFilterTrips = findViewById(R.id.btn_filter_trips);
        tvFilterLabel = findViewById(R.id.tv_filter_label);

        cardTripDalat = findViewById(R.id.card_trip_dalat);
        tvViewDetail = findViewById(R.id.tv_view_detail);
        btnTaoChuyenDi = findViewById(R.id.btn_tao_chuyen_di);
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void xuLySuKienLich() {
        btnPrevMonth.setOnClickListener(v -> {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            tvMonthYear.setText("Tháng " + currentMonth + ", " + currentYear);
        });

        btnNextMonth.setOnClickListener(v -> {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            tvMonthYear.setText("Tháng " + currentMonth + ", " + currentYear);
        });

        tvDay15.setOnClickListener(v -> chonNgay(15));
        tvDay16.setOnClickListener(v -> chonNgay(16));
        tvDay17.setOnClickListener(v -> chonNgay(17));
        tvDay18.setOnClickListener(v -> chonNgay(18));
    }

    private void chonNgay(int day) {
        selectedDay = day;

        tvDay15.setBackground(null);
        tvDay15.setTextColor(Color.parseColor("#191C19"));

        tvDay16.setBackgroundResource(R.drawable.bg_pill_today);
        tvDay16.setTextColor(Color.WHITE);

        tvDay17.setBackground(null);
        tvDay17.setTextColor(Color.parseColor("#191C19"));

        tvDay18.setBackground(null);
        tvDay18.setTextColor(Color.parseColor("#191C19"));

        if (day == 15) {
            tvDay15.setBackgroundResource(R.drawable.bg_pill_selected);
        } else if (day == 16) {
            tvDay16.setBackgroundResource(R.drawable.bg_pill_selected);
            tvDay16.setTextColor(Color.parseColor("#191C19"));
        } else if (day == 17) {
            tvDay17.setBackgroundResource(R.drawable.bg_pill_selected);
        } else if (day == 18) {
            tvDay18.setBackgroundResource(R.drawable.bg_pill_selected);
        }

        String dateText = day + "/" + currentMonth;
        tvLegendSelected.setText("Đang chọn (" + dateText + ")");
        tvViewingDate.setText("Ngày " + dateText);

        if (day >= 15 && day <= 17) {
            cardTripDalat.setVisibility(View.VISIBLE);
        } else {
            cardTripDalat.setVisibility(View.GONE);
            Toast.makeText(this, "Không có lịch trình trong ngày " + dateText, Toast.LENGTH_SHORT).show();
        }
    }

    private void xuLySuKienChuyenDi() {
        btnTaoChuyenDi.setOnClickListener(v ->
                Toast.makeText(this, "Mở màn hình Tạo chuyến đi mới", Toast.LENGTH_SHORT).show()
        );

        View.OnClickListener openDetailListener = v ->
                Toast.makeText(this, "Xem chi tiết: Khám phá Đà Lạt Mộng Mơ", Toast.LENGTH_SHORT).show();
        tvViewDetail.setOnClickListener(openDetailListener);
        cardTripDalat.setOnClickListener(openDetailListener);

        btnFilterTrips.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenu().add("Tất cả (2)");
            popup.getMenu().add("Đang diễn ra");
            popup.getMenu().add("Sắp tới");

            popup.setOnMenuItemClickListener(item -> {
                String choice = item.getTitle().toString();
                tvFilterLabel.setText(choice);

                if (choice.equals("Sắp tới")) {
                    cardTripDalat.setVisibility(View.GONE);
                } else {
                    cardTripDalat.setVisibility(View.VISIBLE);
                }
                return true;
            });
            popup.show();
        });
    }

    private void xuLyBottomNavigation() {
        bottomNavigation.setSelectedItemId(R.id.nav_itinerary);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_itinerary) {
                return true;
            } else if (id == R.id.nav_place) {
                startActivity(new Intent(this, MapsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_home || id == R.id.nav_favorite || id == R.id.nav_profile) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("SELECTED_TAB", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }
}