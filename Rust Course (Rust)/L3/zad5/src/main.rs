fn main() {
}

fn last_digit(a: &str, b: &str) -> i32 {
    fn last_digit_cycle(digit: i32) -> Vec<i32> {
        match digit {
            0 => vec![0, 0, 0, 0],
            1 => vec![1, 1, 1, 1],
            2 => vec![6, 2, 4, 8],
            3 => vec![1, 3, 9, 7],
            4 => vec![6, 4, 6, 4],
            5 => vec![5, 5, 5, 5],
            6 => vec![6, 6, 6, 6],
            7 => vec![1, 7, 9, 3],
            8 => vec![6, 8, 4, 2],
            9 => vec![1, 9, 1, 9],
            _ => panic!("Something went wrong!"),
        }
    }

    let last_digit_of_a = a.chars().last().unwrap().to_digit(10).unwrap() as i32;
    let last_digit_of_b = b.chars().last().unwrap().to_digit(10).unwrap() as i32;
    let cycle = last_digit_cycle(last_digit_of_a);

    if b.len() == 1 {
        if last_digit_of_b == 0 {
            return 1;
        } else {
            return cycle[last_digit_of_b as usize % cycle.len()];
        }
    } else {
        let last_two_digits_of_b = &b[b.len() - 2..].parse::<usize>();
        match last_two_digits_of_b {
                Ok(value) => return cycle[value % 4],
                Err(err) => panic!("{}", err),
            }
        }
    }

#[cfg(test)]
mod tests {
    use super::last_digit;
    
    #[test]
    fn returns_expected1() {
        assert_eq!(last_digit("4", "1"), 4);
    }

    #[test]
    fn returns_expected2() {
        assert_eq!(last_digit("4", "2"), 6);
    }

    #[test]
    fn returns_expected3() {
        assert_eq!(last_digit("9", "7"), 9);
    }

    #[test]
    fn returns_expected4() {
        assert_eq!(last_digit("10", "10000000000"), 0);
    }

    #[test]
    fn returns_expected5() {
        assert_eq!(last_digit("1606938044258990275541962092341162602522202993782792835301376","2037035976334486086268445688409378161051468393665936250636140449354381299763336706183397376"), 6);
    }

    #[test]
    fn returns_expected6() {
        assert_eq!(
            last_digit("3715290469715693021198967285016729344580685479654510946723", "68819615221552997273737174557165657483427362207517952651"), 7);
    }

    #[test]
    fn returns_expected7() {
        assert_eq!(last_digit("999", "0"), 1);
    }

    #[test]
    fn returns_expected8() {
        assert_eq!(last_digit("3897923849837103987908157398275987518978916758907140912", "6910749183274908146781649871467893214678478913648715697834631872543187"), 8);
    }

    #[test]
    fn returns_expected9() {
        assert_eq!(last_digit("21", "37"), 1);
    }

    #[test]
    fn returns_expected10() {
        assert_eq!(last_digit("42", "42"), 4);
    }
}
