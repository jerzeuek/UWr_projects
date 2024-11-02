# Ksawery Plis
# Lista 10 Zadanie 2
# ruby 3.2.3

class Kolekcja

    attr_accessor :head, :tail

    def initialize
      @head = nil
      @tail = nil
    end

    def add_elem(element)
        new_elem = Element.new(element)
        if @head.nil?
            @head = new_elem
            @tail = new_elem
        elsif element < @head.value
            new_elem.next = @head
            @head.prev = new_elem
            @head = new_elem
        elsif element > @tail.value
            new_elem.prev = @tail
            @tail.next = new_elem
            @tail = new_elem
        else
            current = @head.next
            while current != @tail && current.value < element
                current = current.next
            end
            current.prev.next = new_elem
            new_elem.prev = current.prev
            new_elem.next = current
            current.prev = new_elem
        end
    end
end

class Element

    attr_accessor :value, :prev, :next

    def initialize(value)
      @value = value
      @prev = nil
      @next = nil
    end
end

class Wyszukiwanie

    # Wyszukiwanie binarne
    def self.binary(collection, element)
        left = 0
        right = collection.length - 1

        while left <= right
            middle = (left + right) / 2
            if collection[middle] == element
            return middle
            elsif collection[middle] < element
            left = middle + 1
            else
            right = middle - 1
            end
        end
      return nil
    end

    # Wyszukiwanie interpolacyjne
    def self.interpolation(collection, element)
        left = 0
        right = collection.length - 1

        while left <= right && element >= collection[left] && element <= collection[right]
            middle = left + ((element - collection[left]) * (right - left)) / (collection[right] - collection[left])
            if collection[middle] == element
            return middle
            elsif collection[middle] < element
            left = middle + 1
            else
            right = middle - 1
            end
        end
        return nil
    end
end

def test_binary
    puts "Test wyszukiwania binarnego:"
    collection = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    puts "Szukamy w liście [#{collection.join(' ')}]"
    element = 4
    puts "Element #{element.to_s} znajduje się pod indeksem: #{Wyszukiwanie.binary(collection, element)}"
    element = 100
    puts "Element #{element.to_s} znajduje się pod indeksem: #{Wyszukiwanie.binary(collection, element)}"
    puts "(jeśli nie ma wypisanego indeksu to elementu nie ma w liście)"
end

def test_interpolation
    puts "Test wyszukiwania interpolacyjnego:"
    collection = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    puts "Szukamy w liście [#{collection.join(' ')}]"
    element = 7
    puts "Element #{element.to_s} znajduje się pod indeksem: #{Wyszukiwanie.interpolation(collection, element)}"
    element = 42
    puts "Element #{element.to_s} znajduje się pod indeksem: #{Wyszukiwanie.interpolation(collection, element)}"
    puts "(jeśli nie ma wypisanego indeksu to elementu nie ma w liście)"
end


test_binary()
puts
test_interpolation()
