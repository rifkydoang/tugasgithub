/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package method;

import com.controller.controller;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import view.form_aset;
import koneksi.database;

/**
 *
 * @author astse
 */
public class model_siswa implements controller{
String jenisKel;
    @Override
    public void Simpan(form_aset siswa) throws SQLException {
        if (siswa.rbLaki.isSelected()) {
            jenisKel = "Laki-laki";
        } else {
            jenisKel = "Perempuan";
        }
        
        try {
            Connection con = database.getKoneksi();
            String sql = "INSERT INTO siswa VALUES (?,?,?,?)";
            PreparedStatement prr = con.prepareStatement(sql);
            prr.setString(1, siswa.txtNis.getText());
            prr.setString(2, siswa.txtNama.getText());
            prr.setString(3, jenisKel);
            prr.setString(4, (String) siswa.cbJurusan.getSelectedItem());
            prr.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            prr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal di simpan");
            System.err.println("Error "+ e);
        } finally{
            Tampil(siswa);
            Baru(siswa);
            siswa.setLebarKolom();
        }
    }

    @Override
    public void Tampil(form_aset siswa) throws SQLException {
       siswa.tblModel.getDataVector().removeAllElements();
        siswa.tblModel.fireTableDataChanged();
            try {
            Connection con = database.getKoneksi();
            Statement stt = con.createStatement();
            // Query menampilkan semua data pada tabel siswa
            // dengan urutan NIS dari kecil ke besar
            String sql = "SELECT * FROM siswa ORDER BY NIS ASC";
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                siswa.tblModel.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTable(form_aset siswa) throws SQLException {
        try {
            int pilih = siswa.tabel.getSelectedRow();
            if (pilih == -1) {
               return;
            }
            siswa.txtNis.setText(siswa.tblModel.getValueAt(pilih, 0).toString());
            siswa.txtNama.setText(siswa.tblModel.getValueAt(pilih, 1).toString());
            siswa.cbJurusan.setSelectedItem(siswa.tblModel.getValueAt(pilih, 3));
            jenisKel = String.valueOf(siswa.tblModel.getValueAt(pilih, 2));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // memberi nilai jk pada radio button
        if (siswa.rbLaki.getText().equals(jenisKel)) {
            siswa.rbLaki.setSelected(true);
        } else {
            siswa.rbPerempuan.setSelected(true);
        }
    }
    
    public void AutoNumber(form_aset siswa) throws SQLException {
        try {
            Connection con = database.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "SELECT MAX(NIS) FROM siswa";
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                int a = res.getInt(1);
                siswa.txtNis.setText(Integer.toString(a + 1));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void Hapus(form_aset siswa) throws SQLException {
        String sql = "DELETE FROM siswa WHERE NIS=?";
//        String resetno = "ALTER TABLE siswa DROP NIS";
//        String consecutivenumbers = "ALTER TABLE siswa ADD NIS INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
        try{
            Connection con = database.getKoneksi();
            PreparedStatement prepare = con.prepareStatement(sql);
//            con.createStatement().execute(resetno);
//            con.createStatement().execute(consecutivenumbers);
            
            prepare.setString(1, siswa.txtNis.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally{
            Tampil(siswa);
            siswa.setLebarKolom();
            Baru(siswa);
        }
    }
    
    public void Baru(form_aset siswa) throws SQLException {
        siswa.txtNama.setText("");
        siswa.rbLaki.setSelected(true);
        siswa.cbJurusan.setSelectedIndex(0);
    }

    @Override
    public void Ubah(form_aset siswa) throws SQLException {
      if (siswa.rbLaki.isSelected()) {
            jenisKel = "Laki-laki";
        } else {
            jenisKel = "Perempuan";
        }
            try {
                Connection con = database.getKoneksi();
                String sql = "UPDATE siswa SET nama=?, jenis_kelamin=?, " + "jurusan=? WHERE NIS=?";
                PreparedStatement prepare = con.prepareStatement(sql);

                prepare.setString(4, siswa.txtNis.getText());
                prepare.setString(1, siswa.txtNama.getText());
                prepare.setString(2, jenisKel);
                prepare.setString(3, (String) siswa.cbJurusan.getSelectedItem());
                prepare.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
                prepare.close();
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                Tampil(siswa);
                siswa.setLebarKolom();
                Baru(siswa);
            }  
    }
    
}
