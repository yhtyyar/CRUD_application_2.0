package model;

public class Post {

    private Long id;
    private String content;
    private Long writerId;
    private String created;
    private String updated;

    public Post() {
    }

    public Post(String content) {
        if (content == null) {
            this.content = "нет записей";
        } else {
            this.content = content;
        }
    }

    public Post(Long id, String content, String created, String updated, Long writerId) {
        this.id = id;

        if (content == null) {
            this.content = "нет записей";
        } else {
            this.content = content;
        }

        if (created == null) {
            this.created = "запись не создана";
        } else {
            this.created = created;
        }

        if (updated == null) {
            this.created = "запись не изменена";
        } else {
            this.updated = updated;
        }
        this.writerId = writerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public void setContent(String content) {

        if (content == null) {
            this.content = "Записей нет";
        } else {
            this.content = content;
        }
    }

    public void setCreated(String created) {
        if (created == null) {
            this.created = "запись не создана";
        } else {
            this.created = created;
        }
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        if (updated == null) {
            this.created = "запись не изменена";
        } else {
            this.updated = updated;
        }
    }

    @Override
    public String toString() {
        return "  " + id + " | " + writerId + " | " + created + " | " + updated + " | " + content;
    }
}
