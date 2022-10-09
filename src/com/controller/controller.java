/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controller;

import view.form_aset;
import java.sql.SQLException;

/**
 *
 * @author astse
 */
public interface controller {
    public void Simpan(form_aset siswa) throws SQLException;
    public void Hapus(form_aset siswa) throws SQLException;
    public void Tampil(form_aset siswa) throws SQLException;
    public void KlikTable(form_aset siswa) throws SQLException;
}
