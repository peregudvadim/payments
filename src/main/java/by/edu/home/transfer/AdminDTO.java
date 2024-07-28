package by.edu.home.transfer;

public class AdminDTO {

    private int Id;
    private String name;
    private String role;

    public AdminDTO() {
    }

    public AdminDTO(int id, String name, String role) {
        Id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
