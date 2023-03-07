package controller;

import view.View;
import config.Config;
import model.data.Toy;

import java.util.List;

import util.WriteReader;
import view.ConsoleViewImpl;

import java.util.ArrayList;

import model.service.GiftQueue;
import model.service.ToyService;


public class Controller {

    View view;

    public Controller() {
        this.view = new ConsoleViewImpl();
    }

    public void start() {
        ToyService toyService = new ToyService(Config.stockPath);
        GiftQueue giftQueue = new GiftQueue();
        List<String> prizeList = new ArrayList<>();
        WriteReader wr = new WriteReader();
        view.set("\n Добро пожаловать в Магазин игрушек!!!\n");

        Toy temp = new Toy();
        while (true) {
            view.set("""
                    \r=============================================
                    1 - Показать список игрушек
                    2 - Добавить игрушку
                    3 - Изменить частоту выпадения игрушки
                    4 - Произвести розыгрышь игрушек
                    5 - Выдать приз
                    0 - Выход
                    ============================================
                    Выберите пункт меню: 
                    """);
            String key = view.get();
            switch (key) {
                case "1" -> {
                    view.set("\n Всего игрушек: " + toyService.InStockToys().size() + " наименований:");
                    view.set("-".repeat(60));
                    printToyList(toyService.InStockToys());
                    view.set("-".repeat(60));
                }
                case "2" -> {
                    String[] newParam = newToy();
                    if (newParam != null) {
                        temp = toyService.newToy(newParam);
                        view.set("\nДобавлена игрушка: " + temp.getName());
                        view.set("\n ");
                        view.set("" + temp);
                    }
                }
                case "3" -> {
                    int tempInt = chooseID(toyService.InStockToys());
                    if (tempInt == 0) {
                        break;
                    }
                    String tempStr = changeChance();
                    if (tempStr != null) {
                        toyService.changeChance(tempInt, tempStr);
                        view.set("\nЧастота выпадения игрушки изменена!");
                        break;
                    }
                }
                case "4" -> {
                    if (toyService.InStockToys().size() == 0) {
                        view.set("\nВ списке нет игрушек!");
                        break;
                    }
                    temp = giftQueue.fillingQueue(toyService.InStockToys());
                    toyService.decreaseQuantityToy(temp.getId());
                    view.set("\nПроизведен розыгрышь игрушек!!!\nВ список призов добавлена игрушка: " + temp.getName());
                }
                case "5" -> {
                    if (giftQueue.getCurrentQueue().isEmpty()) {
                        view.set("\nОчередь подарков пуста.");
                        break;
                    }
                    temp = giftQueue.getPrize();
                    view.set("\nВыдана игрушка: " + temp.getName());
                    wr.writeCSV(Config.prizePath, String.format("\nid:%d Name: %s\n",
                            temp.getId(), temp.getName()));
                }
                case "0" -> {
                    view.set("\nВы произвели выход из программы!!!");
                    System.exit(0);
                }
                default -> view.set("\nНеверное значение!!! Повторите ввод: ");
            }
        }
    }

    public void printToyList(List<Toy> toys) {
        List<String> toPrint = new ArrayList<>();
        for (Toy toy :
                toys) {
            toPrint.add(toy.toString());
            toPrint.add("\n");
        }
        view.set((toPrint.toString()) + "\r  ");
    }


    public String[] newToy() {
        view.set("\nВведите название новой игрушки: ");
        String name = view.get();
        if (name == null || name.trim().isEmpty()) {
            view.set("\n");
            view.set("\nНазвание отсутствует, отмена!");
            return null;
        }

        view.set("\nКакое будет количество: ");
        String numberOfToys = view.get();
        int numberOfToysInt = 0;
        try {
            numberOfToysInt = Integer.parseInt(numberOfToys);
        } catch (Exception e) {
            view.set("\nНедопустимо, отмена!");
            return null;
        }
        if (numberOfToysInt <= 0) {
            view.set("\nНедопустимо, отмена!");
            return null;
        }
        String change = changeChance();
        if (change == null) {
            return null;
        }
        String result = String.valueOf(Toy.getNumber() + 1) + ";"
                + name + ";" + numberOfToys + ";" + changeChance();
        return result.split(";");
    }

    public int chooseID(List<Toy> toysList) {
        view.set("\nВведите ID игрушки: ");
        String id = view.get();
        int idInd = 0;
        try {
            idInd = Integer.parseInt(id);
        } catch (Exception e) {
            view.set("\nНедопустимо!");
            return 0;
        }
        if (idInd <= 0) {
            view.set("\nID всегда больше 0!");
            return 0;
        } else if (idInd > toysList.size()) {
            view.set("\nID такого нет!");
            return 0;
        }
        return idInd;
    }

    public String changeChance() {
        view.set("\nВведите частоту выпадения в %:  ");
        String chance = view.get();

        if (chance == null || chance.trim().isEmpty()) {
            view.set("\n");
            view.set("\nНедопустимо, отмена!");
            return null;
        }
        float chanceFloat = 0.0f;
        try {
            chanceFloat = Float.parseFloat(chance);
        } catch (Exception e) {
            view.set("\nНедопустимо, отмена!");
            return null;
        }
        if ((int) chanceFloat > 100 || chanceFloat == 0) {
            view.set("\nЧастота выпадения должна быть больше 0% или меньше 100%...");
            return null;
        }
        return chance;
    }
}
