package com.example.projectcuoikiltdd;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import android.content.Intent;

import com.example.projectcuoikiltdd.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Lắng nghe khi người dùng bấm các tab trên thanh Tabbar (Bottom Navigation)
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_itinerary) {
                startActivity(new Intent(this, LichTrinhActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_home) {
                loadEmptyTab("Màn hình: Trang chủ");
                return true;
            } else if (id == R.id.nav_place) {
                loadEmptyTab("Màn hình: Địa điểm");
                return true;
            } else if (id == R.id.nav_favorite) {
                loadEmptyTab("Màn hình: Yêu thích");
                return true;
            } else if (id == R.id.nav_profile) {
                loadEmptyTab("Màn hình: Cá nhân");
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        binding.tvPlaceholder.setVisibility(android.view.View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void loadEmptyTab(String title) {
        // Xóa fragment hiện tại và hiển thị text tab đang chọn
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (current != null) {
            getSupportFragmentManager().beginTransaction().remove(current).commit();
        }
        binding.tvPlaceholder.setVisibility(android.view.View.VISIBLE);
        binding.tvPlaceholder.setText(title + "\n(Màn hình trống)");
    }
}
