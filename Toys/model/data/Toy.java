package model.data;

public class Toy {

    private static int number;
    private int id;
    private String name;
    private int quantity;
    private float dropFrequency;

    public Toy(int id, String name, int quantity, float dropChance) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dropFrequency = dropChance;
    }

    public Toy() {
    }

    @Override
    public String toString() {
        return "\r" + id + " " + name + " = " + quantity + " шт., Частота выпадания = " + dropFrequency + "%";
    }

    public static int newID() {
        return ++number;
    }

    public static int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        Toy.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDropFrequency() {
        return dropFrequency;
    }

    public void setDropFrequency(float dropFrequency) {
        this.dropFrequency = dropFrequency;
    }
}
