# Ksawery Plis
# Lista 10 Zadanie 1
# ruby 3.2.3

# Będzie to w dużej mierze zależeć od wielkości danych
# Pomimo tej samej średniej złożoności czasowej O(n^2)
# sort1 (sortowanie przez wstawianie) będzie zwykle szybszy
# od sort2 (sortowanie babelkowe), ponieważ
# wraz ze wzrostem ilości danych przy sort1
# wykonywana będzie mniejsza ilość zamian elementów niż przy sort2,
# więc im większa ilość danych, tym różnica
# w prędkości między sort1 i sort2 będzie większa na korzyść sort1

class Collection
    def initialize(*elements)
        @data = elements
    end

    def swap(i, j)
        @data[i], @data[j] = @data[j], @data[i]
    end

    def length
        @data.length
    end

    def get(i)
        @data[i]
    end

    def print_all
        puts @data.to_s
    end
end

class Sorter

    # Sortowanie przez wstawianie
    def self.sort1(collection)
      n = collection.length
      (1...n).each do |i|
          key = collection.get(i)
          j = i - 1
          while j >= 0 && collection.get(j) > key
              collection.instance_variable_get(:@data)[j + 1] = collection.get(j)
              j -= 1
          end
          collection.instance_variable_get(:@data)[j + 1] = key
      end
      collection
  end

    # Sortowanie bąbelkowe
    def self.sort2(collection)
        n = collection.length
        loop do
          swapped = false
          (n - 1).times do |i|
            if collection.get(i) > collection.get(i + 1)
              collection.swap(i, i + 1)
              swapped = true
            end
          end
          break unless swapped
        end
        collection
    end
end

def test_sort1
    collection = Collection.new(10, 4, 7, 9, 2, 1, 6, 5, 8, 3)
    puts "Test sortowania przez wstawianie"
    puts "Przed sortowaniem:"
    puts collection.print_all
    sorted_collection = Sorter.sort1(collection)
    puts "Po sortowaniu:"
    puts sorted_collection.print_all
end

def test_sort2
    collection = Collection.new(10, 4, 7, 9, 2, 1, 6, 5, 8, 3)
    puts "Test sortowania bąbelkowego"
    puts "Przed sortowaniem:"
    puts collection.print_all
    sorted_collection = Sorter.sort2(collection)
    puts "Po sortowaniu:"
    puts sorted_collection.print_all
end


test_sort1()
test_sort2()
