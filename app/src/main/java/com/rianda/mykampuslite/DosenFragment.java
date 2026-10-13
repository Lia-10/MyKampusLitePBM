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

public class DosenFragment extends Fragment {

    private AkademikViewModel viewModel;
    private EditText etNama, etEmail, etNidn, etMatkul;
    private Button btnSimpan, btnBatal;
    private PersonAdapter adapter;
    private long selectedId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dosen, container, false);

        etNama = view.findViewById(R.id.etNama);
        etEmail = view.findViewById(R.id.etEmail);
        etNidn = view.findViewById(R.id.etNidn);
        etMatkul = view.findViewById(R.id.etMatkul);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);

        RecyclerView rvDosen = view.findViewById(R.id.rvDosen);
        rvDosen.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PersonAdapter(new PersonAdapter.OnPersonActionListener() {
            @Override
            public void onEdit(Person person) {
                if (person instanceof Dosen) {
                    Dosen d = (Dosen) person;
                    selectedId = d.getId();
                    etNama.setText(d.getNama());
                    etEmail.setText(d.getEmail());
                    etNidn.setText(d.getNidn());
                    etMatkul.setText(d.getMataKuliahDiampu());
                    btnSimpan.setText("Update");
                    btnBatal.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDelete(Person person) {
                if (person instanceof Dosen) {
                    viewModel.deleteDosen((Dosen) person);
                }
            }
        });
        rvDosen.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(AkademikViewModel.class);

        viewModel.getAllDosen().observe(getViewLifecycleOwner(), dosenList -> {
            adapter.setPersonList(dosenList);
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
            boolean success = viewModel.saveDosen(
                    selectedId,
                    etNama.getText().toString(),
                    etEmail.getText().toString(),
                    etNidn.getText().toString(),
                    etMatkul.getText().toString()
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
        etNidn.setText("");
        etMatkul.setText("");
        btnSimpan.setText("Simpan");
        btnBatal.setVisibility(View.GONE);
    }
}