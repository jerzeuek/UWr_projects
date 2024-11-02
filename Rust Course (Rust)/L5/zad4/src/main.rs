fn main() {
}

fn expanded_form(n: u64) -> String {
    n.to_string()
     .chars()
     .rev()
     .enumerate()
     .map(|(i, c)| c.to_string() + &"0".repeat(i))
     .filter(|n| !n.chars().all(|c| c =='0'))
     .collect::<Vec<String>>()
     .into_iter()
     .rev()
     .collect::<Vec<String>>()
     .join(" + ")
}

#[cfg(test)]
mod tests {
    use super::expanded_form;

    #[test]
    fn examples1() {
        assert_eq!(expanded_form(12), "10 + 2");
    }

    #[test]
    fn examples2() {
        assert_eq!(expanded_form(42), "40 + 2");
    }

    #[test]
    fn examples3() {
        assert_eq!(expanded_form(70304), "70000 + 300 + 4");
    }

    #[test]
    fn examples4() {
        assert_eq!(expanded_form(2137), "2000 + 100 + 30 + 7");
    }

    #[test]
    fn examples5() {
        assert_eq!(expanded_form(14320986214380961), "10000000000000000 + 4000000000000000 + 300000000000000 + 20000000000000 + 900000000000 + 80000000000 + 6000000000 + 200000000 + 10000000 + 4000000 + 300000 + 80000 + 900 + 60 + 1");
    }
}