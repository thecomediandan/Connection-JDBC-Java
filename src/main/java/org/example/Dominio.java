package org.example;

public class Dominio {
    private Integer id;
    private String title;
    private Integer rate;

    public Dominio(Integer id, String title, Integer rate) {
        this.id = id;
        this.title = title;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Dominio{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rate=" + rate +
                '}';
    }
}
