fn main() {
}

fn number(bus_stops:&[(i32,i32)]) -> i32 {
    bus_stops.iter()
             .fold(0, |acc, x| acc + x.0 - x.1)   
}

#[cfg(test)]
mod tests {
    use super::number;

    #[test]
    fn returns_expected1() {
        assert_eq!(number(&[(10,0),(3,5),(5,8)]), 5);
    }

    #[test]
    fn returns_expected2() {
        assert_eq!(number(&[(3,0),(9,1),(4,10),(12,2),(6,1),(7,10)]), 17);
    }

    #[test]
    fn returns_expected3() {
        assert_eq!(number(&[(3,0),(9,1),(4,8),(12,2),(6,1),(7,8)]), 21);
    }

    #[test]
    fn returns_expected4() {
        assert_eq!(number(&[(2,0),(1,4),(3,2),(7,6),(6,9),(9,0)]), 7);
    }

    #[test]
    fn returns_expected5() {
        assert_eq!(number(&[(3,0),(1,4),(0,0),(1,1),(2,2),(0,0), (0,1), (2,1)]), 0);
    }

    #[test]
    fn returns_expected6() {
        assert_eq!(number(&[(42, 0)]), 42);
    }

    #[test]
    fn returns_expected7() {
        assert_eq!(number(&[(2, 0), (1, 3), (7, 0)]), 7);
    }

    #[test]
    fn returns_expected8() {
        assert_eq!(number(&[(1, 0), (3, 2), (5, 4), (7, 6), (9, 7)]), 6);
    }

    #[test]
    fn returns_expected9() {
        assert_eq!(number(&[(8, 0), (16, 3), (0, 9), (7, 12)]), 7);
    }

    #[test]
    fn returns_expected10() {
        assert_eq!(number(&[(2, 0), (0, 0), (0, 0)]), 2);
    }

}