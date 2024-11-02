fn main() {
}

fn print(n: i32) -> Option<String> {
    if n % 2 == 0 || n < 0 {
        return None;
    } else {
        let mut res = String::new();
        for i in (1..=n - 2).step_by(2) {
            res += &" ".repeat((n as usize - i as usize) / 2);
            res += &"*".repeat(i as usize);
            res += "\n";
        }

        res += &"*".repeat(n as usize);
        res += "\n";

        for i in (0..=n - 2).rev().step_by(2) {
            res += &" ".repeat((n as usize - i as usize) / 2);
            res += &"*".repeat(i as usize);
            res += "\n";
        }

        return Some(res);
    }
}

#[cfg(test)]
mod tests {
    use super::print;

    #[test]
    fn basic_test1() {
        assert_eq!(print(3), Some(" *\n***\n *\n".to_string()));
    }

    #[test]
    fn basic_test2() {
        assert_eq!(print(5), Some("  *\n ***\n*****\n ***\n  *\n".to_string()));
    }

    #[test]
    fn basic_test3() {
        assert_eq!(print(-3), None);
    }

    #[test]
    fn basic_test4() {
        assert_eq!(print(2), None);
    }

    #[test]
    fn basic_test5() {
        assert_eq!(print(1), Some("*\n".to_string()));
    }
}
