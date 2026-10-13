package com.rianda.mykampuslite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private final OnPersonActionListener listener;

    public interface OnPersonActionListener {
        void onEdit(Person person);
        void onDelete(Person person);
    }

    public PersonAdapter(OnPersonActionListener listener) {
        this.listener = listener;
    }

    public void setPersonList(List<? extends Person> list) {
        this.personList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = personList.get(position);

        // Polymorphism: memanggil method abstract getRole() dari superclass Person
        holder.tvRole.setText(person.getRole());
        holder.tvNama.setText(person.getNama());
        holder.tvEmail.setText(person.getEmail());

        // Polymorphism: memanggil method getStatusAkademik() via Interface EvaluasiAkademik
        if (person instanceof EvaluasiAkademik) {
            String status = ((EvaluasiAkademik) person).getStatusAkademik();
            holder.tvStatus.setText("Status Akademik: " + status);
        }

        if (person instanceof Mahasiswa) {
            Mahasiswa m = (Mahasiswa) person;
            holder.tvDetail1.setText("NIM: " + m.getNim() + " | Jurusan: " + m.getJurusan());
            holder.tvDetail2.setText("IPK: " + m.getIpk());
        } else if (person instanceof Dosen) {
            Dosen d = (Dosen) person;
            holder.tvDetail1.setText("NIDN: " + d.getNidn());
            holder.tvDetail2.setText("Matkul: " + d.getMataKuliahDiampu());
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(person));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(person));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView tvRole, tvNama, tvEmail, tvDetail1, tvDetail2, tvStatus;
        ImageButton btnEdit, btnDelete;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDetail1 = itemView.findViewById(R.id.tvDetail1);
            tvDetail2 = itemView.findViewById(R.id.tvDetail2);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}