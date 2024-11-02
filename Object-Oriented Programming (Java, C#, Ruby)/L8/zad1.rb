# Ksawery Plis
# Lista 8 Zadanie 4
# ruby 3.2.3

class Jawna

    attr_reader :text

    def initialize(text)
      @text = text
    end

    def zaszyfruj(key)
        chars = text.chars.map do |c|
            key[c] || c
        end

        # Zwracamy nowy obiekt klasy Zaszyfrowana
        Zaszyfrowana.new(chars.join)
    end

    def to_s
        @text
    end

end

class Zaszyfrowana

    attr_reader :text

    def initialize(text)
      @text = text
    end

    def odszyfruj(key)
        chars = text.chars.map do |c|
            key.invert[c] || c
        end
        Jawna.new(chars.join)
    end

    def to_s
        @text
    end
end
key = {
    'a' => 'b',
    'b' => 'r',
    'r' => 'y',
    'y' => 'u',
    'u' => 'a'
}

j = Jawna.new('ruby')
z = j.zaszyfruj(key)
puts "Szyfrowanie słowa: " + j.to_s
puts "Oczekiwane: yaru"
puts "Rezultat: " + z.to_s
puts ""

j2 = z.odszyfruj(key)
puts "Odszyfrowanie słowa: " + z.to_s
puts "Oczekiwane: ruby"
puts "Rezultat: " + j2.to_s
