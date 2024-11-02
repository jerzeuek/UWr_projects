// Ksawery Plis
// Lista 5 Zadanie 1
// javac 17.0.8.1

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class OrderedList<T extends Comparable<T>> {
    private List<T> list;

    public OrderedList() {
        list = new ArrayList<>();
    }

    public void addElement(T elem) {
        list.add(elem);
        Collections.sort(list);
    }

    public T getFirst() {
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public String toString() {
        return list.toString();
    }
}

interface PokerHand extends Comparable<PokerHand> {
    String getName();
}

abstract class AbstractPokerHand implements PokerHand {
    private final String name;

    public AbstractPokerHand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(PokerHand other_hand) {
        return Integer.compare(getHandOrder(), ((AbstractPokerHand) other_hand).getHandOrder());
    }

    protected abstract int getHandOrder();
}

class WysokaKarta extends AbstractPokerHand {
    public WysokaKarta() {
        super("Wysoka Karta");
    }

    protected int getHandOrder() {
        return 10;
    }
}

class Para extends AbstractPokerHand {
    public Para() {
        super("Para");
    }

    protected int getHandOrder() {
        return 20;
    }
}

class DwiePary extends AbstractPokerHand {
    public DwiePary() {
        super("Dwie pary");
    }

    protected int getHandOrder() {
        return 30;
    }
}

class Trojka extends AbstractPokerHand {
    public Trojka() {
        super("Trójka");
    }

    protected int getHandOrder() {
        return 40;
    }
}

class Strit extends AbstractPokerHand {
    public Strit() {
        super("Strit");
    }

    protected int getHandOrder() {
        return 50;
    }
}

class Kolor extends AbstractPokerHand {
    public Kolor() {
        super("Kolor");
    }

    protected int getHandOrder() {
        return 60;
    }
}

class Ful extends AbstractPokerHand {
    public Ful() {
        super("Ful");
    }

    protected int getHandOrder() {
        return 70;
    }
}

class Kareta extends AbstractPokerHand {
    public Kareta() {
        super("Kareta");
    }

    protected int getHandOrder() {
        return 80;
    }
}

class Poker extends AbstractPokerHand {
    public Poker() {
        super("Poker");
    }

    protected int getHandOrder() {
        return 90;
    }
}

class PokerKrolewski extends AbstractPokerHand {
    public PokerKrolewski() {
        super("Poker królewski");
    }

    protected int getHandOrder() {
        return 100;
    }
}

public class Zadanie1 {
    public static void main(String[] args) throws Exception {
        PokerHand reka1 = new Poker();
        PokerHand reka2 = new Ful();

        System.out.println("Zobaczmy czy nazwy się zgadzają:");
        System.out.println(reka1.getName().equals("Poker"));
        System.out.println(reka2.getName().equals("Ful"));

        System.out.println("Zobaczmy czy porównania zwracają poprawne wyniki:");
        System.out.println(reka1.compareTo(reka2) > 0);
        System.out.println(reka1.compareTo(reka1) == 0);
        System.out.println(reka2.compareTo(reka2) == 0);
        System.out.println(reka2.compareTo(reka1) < 0);

    };
}
