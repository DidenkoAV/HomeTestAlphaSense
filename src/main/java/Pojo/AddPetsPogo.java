package Pojo;

import java.util.List;

public class AddPetsPogo {
    private String id;
    private String category;
    private String name;
    private List<PhotoUrlsPojo> photoUrls;
    private List<TagsPojo> tags;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhotoUrlsPojo> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<PhotoUrlsPojo> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<TagsPojo> getTags() {
        return tags;
    }

    public void setTags(List<TagsPojo> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddPetsPogo{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }
}
