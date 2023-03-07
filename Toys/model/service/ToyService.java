package model.service;

import model.data.Toy;
import util.WriteReader;

import java.util.ArrayList;
import java.util.List;

public class ToyService {

    private final List<Toy> actualToys;
    private final String config;

    public List<Toy> InStockToys() {
        return actualToys;
    }

    public void addToyToList(Toy toy) {
        this.actualToys.add(toy);
        WriteReader wr = new WriteReader();
        wr.updateDB(this.config, actualToys);
    }

    public ToyService(String config) {
        this.actualToys = new ArrayList<>();
        this.config = config;
        WriteReader wr = new WriteReader();
        String[] temp = wr.read(this.config).split("\n");

        if (temp.length > 1) {
            for (String item :
                    temp) {
                Toy toy = createToyFromDB(item.split(";"));
                actualToys.add(toy);
                if (Toy.getNumber() < toy.getId()) {
                    Toy.setNumber(toy.getId());
                }
            }
        }
    }

    public Toy createToyFromDB(String[] param) {
        return new Toy(Integer.parseInt(param[0]),
                param[1],
                Integer.parseInt(param[2]),
                Float.parseFloat(param[3]));
    }

    public Toy newToy(String[] param) {
        Toy toy = createToyFromDB(param);
        this.actualToys.add(toy);
        if (Toy.getNumber() < toy.getId()) {
            Toy.setNumber(toy.getId());
        }
        WriteReader wr = new WriteReader();
        wr.updateDB(this.config, actualToys);
        return toy;
    }

    public void removeToy(int id) {
        for (Toy item :
                this.actualToys) {
            if (item.getId() == id) {
                this.actualToys.remove(item);
                WriteReader wr = new WriteReader();
                wr.updateDB(this.config, this.actualToys);
                return;
            }
        }
    }

    public void changeChance(int id, String param) {
        for (Toy toy :
                this.actualToys) {
            if (id == toy.getId()) {
                toy.setDropFrequency(Float.parseFloat(param));
                WriteReader wr = new WriteReader();
                wr.updateDB(this.config, this.actualToys);
                return;
            }
        }
    }

    public void decreaseQuantityToy(int id) {
        for (Toy item :
                this.actualToys) {
            if (item.getId() == id) {
                item.setQuantity(item.getQuantity() - 1);
                if (item.getQuantity() == 0) {
                    removeToy(item.getId());
                    return;
                }
                WriteReader wr = new WriteReader();
                wr.updateDB(this.config, this.actualToys);
                return;
            }
        }
    }

}
