fn main() {}

fn gimme_the_letters(sp: &str) -> String {
    (sp.chars()
       .nth(0)
       .unwrap()..=sp.chars()
                     .nth(2)
                     .unwrap()).collect()
}

#[cfg(test)]
mod tests {
    use super::gimme_the_letters;
        
    fn dotest(sp: &str, expected: &str) {
        let actual = gimme_the_letters(sp);
        assert!(actual == expected, 
            "With sp = \"{sp}\"\nExpected \"{expected}\" but got \"{actual}\"")
    }

    #[test]
    fn fixed_tests1() {
        dotest("a-z", "abcdefghijklmnopqrstuvwxyz");
    }

    #[test]
    fn fixed_tests2() {
        dotest("h-o", "hijklmno");
    }

    #[test]
    fn fixed_tests3() {
        dotest("Q-Z", "QRSTUVWXYZ");
    }

    #[test]
    fn fixed_tests4() {
        dotest("J-J", "J");
    }

    #[test]
    fn fixed_tests5() {
        dotest("a-b", "ab");
    }
}