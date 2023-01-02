package vn.embosua.ltddquanlynhanvien.modle;

public class nhanvien {

    private String fullName;// ho va ten
//    private ImageView imgStaff; // anh nhan vien
    private String dateOfBirth;// ngay sinh
    private String numberPhone;// so dien thoai
    private String email; // email
    private String cmnd;// so chung minh nhan dan
    private String startDay; // ngay vao cong ty lam // ngay bat dau
    private String positions; // chuc vu
    private String id; // id do huawei cap
    private String coefficientsSalary; // he so luong
    private String admin; //


    public nhanvien(String fullName, String dateOfBirth, String numberPhone, String email, String cmnd, String startDay, String positions, String id, String coefficientsSalary, String admin) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.numberPhone = numberPhone;
        this.email = email;
        this.cmnd = cmnd;
        this.startDay = startDay;
        this.positions = positions;
        this.id = id;
        this.coefficientsSalary = coefficientsSalary;
        this.admin = admin;
    }

    public nhanvien() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoefficientsSalary() {
        return coefficientsSalary;
    }

    public void setCoefficientsSalary(String coefficientsSalary) {
        this.coefficientsSalary = coefficientsSalary;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "nhanvien{" +
                "fullName='" + fullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", email='" + email + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", startDay='" + startDay + '\'' +
                ", positions='" + positions + '\'' +
                ", id='" + id + '\'' +
                ", coefficientsSalary='" + coefficientsSalary + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
