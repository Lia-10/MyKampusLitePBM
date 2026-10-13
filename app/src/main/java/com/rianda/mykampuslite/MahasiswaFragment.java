package com.rianda.mykampuslite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MahasiswaFragment extends Fragment {

    private AkademikViewModel viewModel;
    private EditText etNama, etEmail, etNim, etJurusan, etIpk;
    private Button btnSimpan, btnBatal;
    private PersonAdapter adapter;
    private long selectedId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);

        etNama = view.findViewById(R.id.etNama);
        etEmail = view.findViewById(R.id.etEmail);
        etNim = view.findViewById(R.id.etNim);
        etJurusan = view.findViewById(R.id.etJurusan);
        etIpk = view.findViewById(R.id.etIpk);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);

        RecyclerView rvMahasiswa = view.findViewById(R.id.rvMahasiswa);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PersonAdapter(new PersonAdapter.OnPersonActionListener() {
            @Override
            public void onEdit(Person person) {
                if (person instanceof Mahasiswa) {
                    Mahasiswa m = (Mahasiswa) person;
                    selectedId = m.getId();
                    etNama.setText(m.getNama());
                    etEmail.setText(m.getEmail());
                    etNim.setText(m.getNim());
                    etJurusan.setText(m.getJurusan());
                    etIpk.setText(String.valueOf(m.getIpk()));
                    btnSimpan.setText("Update");
                    btnBatal.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDelete(Person person) {
                if (person instanceof Mahasiswa) {
                    viewModel.deleteMahasiswa((Mahasiswa) person);
                }
            }
        });
        rvMahasiswa.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(AkademikViewModel.class);

        viewModel.getAllMahasiswa().observe(getViewLifecycleOwner(), mahasiswaList -> {
            adapter.setPersonList(mahasiswaList);
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), err -> {
            if (err != null && !err.isEmpty()) {
                Toast.makeText(getContext(), "Error: " + err, Toast.LENGTH_LONG).show();
            }
        });

        btnSimpan.setOnClickListener(v -> {
            boolean success = viewModel.saveMahasiswa(
                    selectedId,
                    etNama.getText().toString(),
                    etEmail.getText().toString(),
                    etNim.getText().toString(),
                    etJurusan.getText().toString(),
                    etIpk.getText().toString()
            );
            if (success) resetForm();
        });

        btnBatal.setOnClickListener(v -> resetForm());

        return view;
    }

    private void resetForm() {
        selectedId = -1;
        etNama.setText("");
        etEmail.setText("");
        etNim.setText("");
        etJurusan.setText("");
        etIpk.setText("");
        btnSimpan.setText("Simpan");
        btnBatal.setVisibility(View.GONE);
    }
}