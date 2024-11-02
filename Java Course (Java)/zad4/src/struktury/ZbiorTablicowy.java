package struktury;

public class ZbiorTablicowy implements Zbior, Cloneable {
    private Para[] zbiór;
    private int zapełnienie;


    public ZbiorTablicowy(int rozmiar) {
        this.zbiór = new Para[rozmiar];
        this.zapełnienie = 0;
    }

    @Override
    public Para szukaj(String k) {
        for (Para para : zbiór) {
            if (para != null && para.klucz.equals(k)) {
                return para;
            }
        }
        return null;
    }

    @Override
    public void wstaw(Para p) {
        Para istniejącaPara = szukaj(p.klucz);
        if (istniejącaPara != null) {
            istniejącaPara.setWartość(p.getWartość());
        } else {
            if (zapełnienie < zbiór.length) {
                zbiór[zapełnienie++] = p;
            } else {
                throw new IllegalStateException("Zbiór jest już pełny.");
            }
        }
    }

    @Override
    public void usuń(String k) {
        for (int i = 0; i < zapełnienie; i++) {
            if (zbiór[i].klucz.equals(k)) {
                zbiór[i] = null;
                przesuńTablicę(i);
                zapełnienie--;
                break;
            }
        }
    }

    @Override
    public void czysc() {
        for (int i = 0; i < zapełnienie; i++) {
            zbiór[i] = null;
        }
        zapełnienie = 0;
    }

    @Override
    public int ile() {
        return zapełnienie;
    }

    private void przesuńTablicę(int indeks) {
        for (int i = indeks; i < zapełnienie - 1; i++) {
            zbiór[i] = zbiór[i + 1];
        }
    }

    @Override
    public ZbiorTablicowy clone() {
        try {
            ZbiorTablicowy klon = (ZbiorTablicowy) super.clone();
            klon.zbiór = zbiór.clone();
            return klon;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}