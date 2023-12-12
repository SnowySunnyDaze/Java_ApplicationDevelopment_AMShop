package gui;

import dao.DAO_ChatLieu;
import dao.DAO_DanhMuc;
import dao.DAO_GioiTinh;
import dao.DAO_KichThuoc;
import dao.DAO_MauSac;
import dao.DAO_NhaSanXuat;
import dao.DAO_QuanAo;
import data.FormatDouble;
import data.GenerateID;
import data.UtilityImageIcon;
import entity.QuanAo;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jnafilechooser.api.JnaFileChooser;

public class GUI_ThemQuanAo extends javax.swing.JPanel implements ItemListener {
    
    private static GUI_ThemQuanAo instance = new GUI_ThemQuanAo();
    
    private String imagePath = "";

    public static GUI_ThemQuanAo getInstance() {
        return instance;
    }
    
    public static GUI_ThemQuanAo newInstance() {
        instance = new GUI_ThemQuanAo();
        return instance;
    }
    

    public GUI_ThemQuanAo() {
        initComponents();
        initExtra();
    }
    
    private void initExtra(){
        updateTable(DAO_QuanAo.getAllQuanAo());
        
        txtMaQuanAo.setText(GenerateID.generateMaQuanAo());
        txtMaQuanAo.setEditable(false);
        
        cmbNhaSanXuat.addItemListener(this);
        cmbDanhMuc.addItemListener(this);
        cmbChatLieu.addItemListener(this);
        
        updateNhaSanXuat();
        updateDanhMuc();
        updateChatLieu();
        updateGioiTinh();
        updateMauSac();
        updateKichThuoc();
        
        tblTable.fixTable(scrTable);
    }
    
    private void updateNhaSanXuat(){
        ArrayList<String> listNhaSanXuat = DAO_NhaSanXuat.getAllNhaSanXuat();
        cmbNhaSanXuat.removeAllItems();
        cmbNhaSanXuat.addItem("Nhà Sản Xuất");
        for(String thisNhaSanXuat : listNhaSanXuat)
            cmbNhaSanXuat.addItem(thisNhaSanXuat);
        cmbNhaSanXuat.addItem("-- Nhà Sản Xuất Mới --");
    }
    
    private void updateDanhMuc(){
        ArrayList<String> listDanhMuc = DAO_DanhMuc.getAllDanhMuc();
        cmbDanhMuc.removeAllItems();
        cmbDanhMuc.addItem("Danh Mục");
        for(String thisDanhMuc : listDanhMuc)
            cmbDanhMuc.addItem(thisDanhMuc);
        cmbDanhMuc.addItem("-- Danh Mục Mới --");
    }
    
    private void updateChatLieu(){
        ArrayList<String> listChatLieu= DAO_ChatLieu.getAllChatLieu();
        cmbChatLieu.removeAllItems();
        cmbChatLieu.addItem("Chất Liệu");
        for(String thisChatLieu : listChatLieu)
            cmbChatLieu.addItem(thisChatLieu);
        cmbChatLieu.addItem("-- Chất Liệu Mới --");
    }
    
    private void updateGioiTinh(){
        ArrayList<String> listGioiTinh = DAO_GioiTinh.getAllGioiTinh();
        for(String thisGioiTinh : listGioiTinh)
            cmbGioiTinh.addItem(thisGioiTinh);
    }
    
    private void updateMauSac(){
         ArrayList<String> listMauSac = DAO_MauSac.getAllMauSac();
         for(String thisMauSac : listMauSac)
            cmbMauSac.addItem(thisMauSac);
    }
    
    private void updateKichThuoc(){
        ArrayList<String> listKichThuoc = DAO_KichThuoc.getAllKichThuoc();
        for(String thisKichThuoc : listKichThuoc)
            cmbKichThuoc.addItem(thisKichThuoc);
    }
    
    private void updateTable(ArrayList<QuanAo> list){
        DefaultTableModel model = (DefaultTableModel) tblTable.getModel();
        model.getDataVector().removeAllElements();
        tblTable.revalidate();
        tblTable.repaint();
        for(QuanAo thisQuanAo : list){
            model.addRow(new Object[]{
                thisQuanAo.getMaQuanAo(),
                thisQuanAo.getTenQuanAo(),
                FormatDouble.toMoney(thisQuanAo.getDonGiaNhap()),
                FormatDouble.toMoney(thisQuanAo.getDonGiaBan()),
                thisQuanAo.getSoLuongTrongKho(),
                thisQuanAo.getNhaSanXuat(),
                thisQuanAo.getDanhMuc(),
                thisQuanAo.getGioiTinh(),
                thisQuanAo.getMauSac(),
                thisQuanAo.getKichThuoc(),
                thisQuanAo.getChatLieu()
            });
        }
    }
    
    private void themQuanAo(){
        String error = "";
        
        String maQuanAo = txtMaQuanAo.getText();
        String tenQuanAo = txtTenQuanAo.getText();
        String donGiaNhapString = txtDonGiaNhap.getText();
        String donGiaBanString = txtDonGiaBan.getText();
        String soLuongString = txtSoLuong.getText();
        String nhaSanXuat = cmbNhaSanXuat.getSelectedItem().toString();
        String danhMuc = cmbDanhMuc.getSelectedItem().toString();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        String mauSac = cmbMauSac.getSelectedItem().toString();
        String kichThuoc = cmbKichThuoc.getSelectedItem().toString();
        String chatLieu = cmbChatLieu.getSelectedItem().toString();
        
        double donGiaNhap = 0;
        double donGiaBan = 0;
        int soLuong = 0;
        ImageIcon hinhAnh = UtilityImageIcon.fromStringPath(imagePath, 196, 270);
        
        if(tenQuanAo.equals(""))
            error += "\n- Vui lòng nhập Tên Quần Áo.";
        
        try{
            donGiaNhap = Double.parseDouble(donGiaNhapString);
            if(donGiaNhap < 0)
                error += "\n- Đơn Giá Nhập phải lớn hơn 0.";
        }
        catch(NumberFormatException e){
            error += "\n- Vui lòng nhập Đơn Giá Nhập hợp lệ.";
        }
        
        try{
            donGiaBan = Double.parseDouble(donGiaBanString);
            if(donGiaBan < 0)
                error += "\n- Đơn Giá Bán phải lớn hơn 0.";
            else
                if(donGiaBan < donGiaNhap)
                    error += "\n- Đơn Giá Bán phải lớn hơn Đơn Giá Nhập.";
        }
        catch(NumberFormatException e){
            error += "\n- Vui lòng nhập Đơn Giá Bán hợp lệ.";
        }
        
        try{
            soLuong = Integer.parseInt(soLuongString);
            if(soLuong < 0)
                error += "\n- Số Lượng phải lớn hơn 0.";
        }
        catch(NumberFormatException e){
            error += "\n- Vui lòng nhập Số Lượng hợp lệ.";
        }
        
        if(nhaSanXuat.equals("Nhà Sản Xuất"))
            error += "\n -Vui lòng chọn Nhà Sản Xuất.";
        
        if(danhMuc.equals("Danh Mục"))
            error += "\n -Vui lòng chọn Danh Mục.";
        
        if(chatLieu.equals("Chất Liệu"))
            error += "\n -Vui lòng chọn Chất Liệu.";
        
        if(mauSac.equals("Màu Sắc"))
            error += "\n -Vui lòng chọn Màu Sắc.";
        
        if(kichThuoc.equals("Kích Thước"))
            error += "\n -Vui lòng chọn Kích Thước.";
        
        if(gioiTinh.equals("Giới Tính"))
            error += "\n -Vui lòng chọn Giới Tính.";
        
        if(imagePath.equals(""))
            error += "\n -Vui lòng chọn Hình Ảnh.";
        
        if(error.equals("")){
            QuanAo quanAo = new QuanAo(maQuanAo, tenQuanAo, donGiaNhap, donGiaBan, soLuong, nhaSanXuat, danhMuc, gioiTinh, mauSac, kichThuoc, chatLieu, hinhAnh, false);
            if(DAO_QuanAo.createQuanAo(quanAo) == true){
                JOptionPane.showMessageDialog(null, "Thêm Quần Áo thành công.");
                GUI_Main.getInstance().showPanel(newInstance());
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm Quần Áo thất bại.");
            }
        }
        else{
            String throwMessage = "Lỗi nhập liệu: " + error;
            JOptionPane.showMessageDialog(null, throwMessage);
        }
    }
    
    private void themNhaSanXuat(){
        String nhaSanXuat = JOptionPane.showInputDialog(null, "Thêm Nhà Sản Xuất Mới", "Nhập Tên Nhà Sản Xuất Mới:", JOptionPane.YES_NO_CANCEL_OPTION);
        if(nhaSanXuat == null || nhaSanXuat.equals("")){
            cmbNhaSanXuat.setSelectedItem("Nhà Sản Xuất");
            return;
        }
        boolean kiemTraTonTai = DAO_NhaSanXuat.kiemTraTonTai(nhaSanXuat);
        if(kiemTraTonTai){
            JOptionPane.showMessageDialog(null, "Nhà Sản Xuất này đã tồn tại.");
            cmbNhaSanXuat.setSelectedItem("Nhà Sản Xuất");
        }
        else{
            boolean kiemTra = DAO_NhaSanXuat.createNhaSanXuat(nhaSanXuat);
            if(kiemTra){
                JOptionPane.showMessageDialog(null, "Thêm Nhà Sản Xuất thành công.");
                updateNhaSanXuat();
                cmbNhaSanXuat.setSelectedItem(nhaSanXuat);
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm Nhà Sản Xuất thất bại.");
                cmbNhaSanXuat.setSelectedItem("Nhà Sản Xuất");
            }
        }
    }
    
    private void themDanhMuc(){
        String danhMuc = JOptionPane.showInputDialog(null, "Thêm Danh Mục Mới", "Nhập Tên Danh Mục Mới:", JOptionPane.YES_NO_CANCEL_OPTION);
        if(danhMuc == null || danhMuc.equals("")){
            cmbDanhMuc.setSelectedItem("Danh Mục");
            return;
        }
        boolean kiemTraTonTai = DAO_DanhMuc.kiemTraTonTai(danhMuc);
        if(kiemTraTonTai){
            JOptionPane.showMessageDialog(null, "Danh Mục này đã tồn tại.");
            cmbDanhMuc.setSelectedItem("Danh Mục");
        }
        else{
            boolean kiemTra = DAO_DanhMuc.createDanhMuc(danhMuc);
            if(kiemTra){
                JOptionPane.showMessageDialog(null, "Thêm Danh Mục thành công.");
                updateDanhMuc();
                cmbDanhMuc.setSelectedItem(danhMuc);
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm Danh Mục thất bại.");
                cmbDanhMuc.setSelectedItem("Danh Mục");
            }
        }
    }
    
    private void themChatLieu(){
        String chatLieu = JOptionPane.showInputDialog(null, "Thêm Chất Liệu Mới", "Nhập Tên Chất Liệu Mới:", JOptionPane.YES_NO_CANCEL_OPTION);
        if(chatLieu == null || chatLieu.equals("")){
            cmbChatLieu.setSelectedItem("Chất Liệu");
            return;
        }
        boolean kiemTraTonTai = DAO_ChatLieu.kiemTraTonTai(chatLieu);
        if(kiemTraTonTai){
            JOptionPane.showMessageDialog(null, "Chất Liệu này đã tồn tại.");
            cmbChatLieu.setSelectedItem("Chất Liệu");
        }
        else{
            boolean kiemTra = DAO_ChatLieu.createChatLieu(chatLieu);
            if(kiemTra){
                JOptionPane.showMessageDialog(null, "Thêm Chất Liệu thành công.");
                updateChatLieu();
                cmbChatLieu.setSelectedItem(chatLieu);
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm Chất Liệu thất bại.");
                cmbChatLieu.setSelectedItem("Chất Liệu");
            }
        }
    }
    
    private void chonHinhAnh(){
        JnaFileChooser fileChooser = new JnaFileChooser();
        File directory = new File("files//hinhAnh//");
        fileChooser.setCurrentDirectory(directory.getAbsolutePath());
        boolean action = fileChooser.showOpenDialog(null);
        if(action){
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            lblIMG.setText("");
            lblIMG.setIcon(UtilityImageIcon.fromStringPath(imagePath, 194, 270));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNorth = new javax.swing.JPanel();
        lblMaQuanAo = new javax.swing.JLabel();
        txtMaQuanAo = new extended_JComponent.JTextField_AllRound();
        lblTenQuanAo = new javax.swing.JLabel();
        txtTenQuanAo = new extended_JComponent.JTextField_AllRound();
        lblDonGiaNhap = new javax.swing.JLabel();
        txtDonGiaNhap = new extended_JComponent.JTextField_AllRound();
        lblDonGiaBan = new javax.swing.JLabel();
        txtDonGiaBan = new extended_JComponent.JTextField_AllRound();
        lblSoLuong = new javax.swing.JLabel();
        txtSoLuong = new extended_JComponent.JTextField_AllRound();
        lblNhaSanXuat = new javax.swing.JLabel();
        cmbNhaSanXuat = new javax.swing.JComboBox<>();
        lblDanhMuc = new javax.swing.JLabel();
        cmbDanhMuc = new javax.swing.JComboBox<>();
        lblChatLieu = new javax.swing.JLabel();
        cmbChatLieu = new javax.swing.JComboBox<>();
        lblMauSac = new javax.swing.JLabel();
        cmbMauSac = new javax.swing.JComboBox<>();
        lblKichThuoc = new javax.swing.JLabel();
        cmbKichThuoc = new javax.swing.JComboBox<>();
        lblGioiTinh = new javax.swing.JLabel();
        cmbGioiTinh = new javax.swing.JComboBox<>();
        lblHinhAnh = new javax.swing.JLabel();
        btnHinhAnh = new extended_JComponent.JButton_AllRound();
        pnlHinhAnh = new javax.swing.JPanel();
        lblIMG = new javax.swing.JLabel();
        btnThemQuanAo = new extended_JComponent.JButton_AllRound();
        btnLamMoi = new extended_JComponent.JButton_AllRound();
        pnlCenter = new javax.swing.JPanel();
        scrTable = new javax.swing.JScrollPane();
        tblTable = new extended_JComponent.JTable_LightMode();

        setLayout(new java.awt.BorderLayout());

        pnlNorth.setBackground(new java.awt.Color(68, 136, 255));
        pnlNorth.setPreferredSize(new java.awt.Dimension(1166, 400));

        lblMaQuanAo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaQuanAo.setForeground(new java.awt.Color(255, 255, 255));
        lblMaQuanAo.setText("Mã Quần Áo");

        txtMaQuanAo.setEditable(false);
        txtMaQuanAo.setBackground(new java.awt.Color(204, 204, 204));

        lblTenQuanAo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenQuanAo.setForeground(new java.awt.Color(255, 255, 255));
        lblTenQuanAo.setText("Tên Quần Áo");

        lblDonGiaNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDonGiaNhap.setForeground(new java.awt.Color(255, 255, 255));
        lblDonGiaNhap.setText("Đơn Giá Nhập");

        lblDonGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDonGiaBan.setForeground(new java.awt.Color(255, 255, 255));
        lblDonGiaBan.setText("Đơn Giá Bán");

        lblSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSoLuong.setForeground(new java.awt.Color(255, 255, 255));
        lblSoLuong.setText("Số Lượng");

        lblNhaSanXuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNhaSanXuat.setForeground(new java.awt.Color(255, 255, 255));
        lblNhaSanXuat.setText("Nhà Sản Xuất");

        cmbNhaSanXuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbNhaSanXuat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhà Sản Xuất" }));
        cmbNhaSanXuat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbNhaSanXuatItemStateChanged(evt);
            }
        });

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        lblDanhMuc.setText("Danh Mục");

        cmbDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh Mục" }));
        cmbDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDanhMucItemStateChanged(evt);
            }
        });

        lblChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblChatLieu.setForeground(new java.awt.Color(255, 255, 255));
        lblChatLieu.setText("Chất Liệu");

        cmbChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chất Liệu" }));
        cmbChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbChatLieuItemStateChanged(evt);
            }
        });

        lblMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMauSac.setForeground(new java.awt.Color(255, 255, 255));
        lblMauSac.setText("Màu Sắc");

        cmbMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Màu Sắc" }));

        lblKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKichThuoc.setForeground(new java.awt.Color(255, 255, 255));
        lblKichThuoc.setText("Kích Thước");

        cmbKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kích Thước" }));

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGioiTinh.setForeground(new java.awt.Color(255, 255, 255));
        lblGioiTinh.setText("Giới Tính");

        cmbGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giới Tính" }));

        lblHinhAnh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHinhAnh.setForeground(new java.awt.Color(255, 255, 255));
        lblHinhAnh.setText("Hình Ảnh");

        btnHinhAnh.setText("Chọn Hình Ảnh");
        btnHinhAnh.setBorderRadius(30);
        btnHinhAnh.setColorBackground(new java.awt.Color(170, 238, 255));
        btnHinhAnh.setColorBorder(new java.awt.Color(255, 255, 255));
        btnHinhAnh.setColorClick(new java.awt.Color(119, 204, 255));
        btnHinhAnh.setColorEnter(new java.awt.Color(119, 238, 255));
        btnHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnhActionPerformed(evt);
            }
        });

        pnlHinhAnh.setBackground(new java.awt.Color(204, 204, 204));
        pnlHinhAnh.setLayout(new java.awt.GridBagLayout());

        lblIMG.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIMG.setText("IMG");
        pnlHinhAnh.add(lblIMG, new java.awt.GridBagConstraints());

        btnThemQuanAo.setText("Thêm Quần Áo");
        btnThemQuanAo.setBorderRadius(30);
        btnThemQuanAo.setColorBackground(new java.awt.Color(170, 238, 255));
        btnThemQuanAo.setColorBorder(new java.awt.Color(255, 255, 255));
        btnThemQuanAo.setColorClick(new java.awt.Color(119, 204, 255));
        btnThemQuanAo.setColorEnter(new java.awt.Color(119, 238, 255));
        btnThemQuanAo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemQuanAoActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm Mới Màn Hình");
        btnLamMoi.setBorderRadius(30);
        btnLamMoi.setColorBackground(new java.awt.Color(170, 238, 255));
        btnLamMoi.setColorBorder(new java.awt.Color(255, 255, 255));
        btnLamMoi.setColorClick(new java.awt.Color(119, 204, 255));
        btnLamMoi.setColorEnter(new java.awt.Color(119, 238, 255));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNorthLayout = new javax.swing.GroupLayout(pnlNorth);
        pnlNorth.setLayout(pnlNorthLayout);
        pnlNorthLayout.setHorizontalGroup(
            pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorthLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNorthLayout.createSequentialGroup()
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDonGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDonGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMaQuanAo, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTenQuanAo, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                .addComponent(txtDonGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDonGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNorthLayout.createSequentialGroup()
                                .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlNorthLayout.createSequentialGroup()
                                .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlNorthLayout.createSequentialGroup()
                                .addComponent(lblNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlNorthLayout.createSequentialGroup()
                                .addComponent(lblChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(69, 69, 69))
                    .addGroup(pnlNorthLayout.createSequentialGroup()
                        .addComponent(btnThemQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(pnlHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        pnlNorthLayout.setVerticalGroup(
            pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorthLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlNorthLayout.createSequentialGroup()
                        .addComponent(pnlHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(pnlNorthLayout.createSequentialGroup()
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMaQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaQuanAo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlNorthLayout.createSequentialGroup()
                                    .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblTenQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(cmbNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(lblDonGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlNorthLayout.createSequentialGroup()
                                    .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlNorthLayout.createSequentialGroup()
                                .addComponent(txtTenQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDonGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblDonGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDonGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlNorthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThemQuanAo, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(86, 86, 86))))
        );

        add(pnlNorth, java.awt.BorderLayout.NORTH);

        pnlCenter.setLayout(new java.awt.BorderLayout());

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Quần Áo", "Tên Quần Áo", "Đơn Giá Nhập", "Đơn Giá Bán", "Số Lượng", "Nhà Sản Xuất", "Danh Mục", "Giới Tính", "Màu Sắc", "Kích Thước", "Chất Liệu"
            }
        ));
        scrTable.setViewportView(tblTable);
        if (tblTable.getColumnModel().getColumnCount() > 0) {
            tblTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        pnlCenter.add(scrTable, java.awt.BorderLayout.CENTER);

        add(pnlCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnhActionPerformed
        // TODO add your handling code here:
        chonHinhAnh();
    }//GEN-LAST:event_btnHinhAnhActionPerformed

    private void btnThemQuanAoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemQuanAoActionPerformed
        // TODO add your handling code here:
        themQuanAo();
    }//GEN-LAST:event_btnThemQuanAoActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        GUI_Main.getInstance().showPanel(newInstance());
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cmbNhaSanXuatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbNhaSanXuatItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            if(cmbNhaSanXuat.getSelectedItem().toString().equals("-- Nhà Sản Xuất Mới --")){
                themNhaSanXuat();
            }  
        }
    }//GEN-LAST:event_cmbNhaSanXuatItemStateChanged

    private void cmbDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDanhMucItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            if(cmbDanhMuc.getSelectedItem().toString().equals("-- Danh Mục Mới --")){
                themDanhMuc();
            }  
        }
    }//GEN-LAST:event_cmbDanhMucItemStateChanged

    private void cmbChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbChatLieuItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            if(cmbChatLieu.getSelectedItem().toString().equals("-- Chất Liệu Mới --")){
                themChatLieu();
            }  
        }
    }//GEN-LAST:event_cmbChatLieuItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private extended_JComponent.JButton_AllRound btnHinhAnh;
    private extended_JComponent.JButton_AllRound btnLamMoi;
    private extended_JComponent.JButton_AllRound btnThemQuanAo;
    private javax.swing.JComboBox<String> cmbChatLieu;
    private javax.swing.JComboBox<String> cmbDanhMuc;
    private javax.swing.JComboBox<String> cmbGioiTinh;
    private javax.swing.JComboBox<String> cmbKichThuoc;
    private javax.swing.JComboBox<String> cmbMauSac;
    private javax.swing.JComboBox<String> cmbNhaSanXuat;
    private javax.swing.JLabel lblChatLieu;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDonGiaBan;
    private javax.swing.JLabel lblDonGiaNhap;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblIMG;
    private javax.swing.JLabel lblKichThuoc;
    private javax.swing.JLabel lblMaQuanAo;
    private javax.swing.JLabel lblMauSac;
    private javax.swing.JLabel lblNhaSanXuat;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenQuanAo;
    private javax.swing.JPanel pnlCenter;
    private javax.swing.JPanel pnlHinhAnh;
    private javax.swing.JPanel pnlNorth;
    private javax.swing.JScrollPane scrTable;
    private extended_JComponent.JTable_LightMode tblTable;
    private extended_JComponent.JTextField_AllRound txtDonGiaBan;
    private extended_JComponent.JTextField_AllRound txtDonGiaNhap;
    private extended_JComponent.JTextField_AllRound txtMaQuanAo;
    private extended_JComponent.JTextField_AllRound txtSoLuong;
    private extended_JComponent.JTextField_AllRound txtTenQuanAo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if(o == cmbNhaSanXuat){
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(cmbNhaSanXuat.getSelectedItem().toString().equals("-- Nhà Sản Xuất Mới --")){
                    themNhaSanXuat();
                }  
            }
        }
        if(o == cmbDanhMuc){
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(cmbDanhMuc.getSelectedItem().toString().equals("-- Danh Mục Mới --")){
                    themDanhMuc();
                }  
            }
        }
        if(o == cmbChatLieu){
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(cmbChatLieu.getSelectedItem().toString().equals("-- Chất Liệu Mới --")){
                    themChatLieu();
                }  
            }
        }
    }

}