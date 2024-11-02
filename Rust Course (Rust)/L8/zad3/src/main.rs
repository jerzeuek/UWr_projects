fn main() {
}

fn part_list(arr: Vec<&str>) -> String {
    let mut res = String::new();

    for i in 1..arr.len() {
        res += format!("({}, {})", &arr[..i].join(" "), &arr[i..].join(" ")).as_str();
    }
    res
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn basis_tests1() {
        assert_eq!(part_list(vec!["I", "wish", "I", "hadn't", "come"]),
                "(I, wish I hadn't come)(I wish, I hadn't come)(I wish I, hadn't come)(I wish I hadn't, come)");
    }

    #[test]
    fn basis_tests2() {
        assert_eq!(
            part_list(vec!["cdIw", "tzIy", "xDu", "rThG"]),
            "(cdIw, tzIy xDu rThG)(cdIw tzIy, xDu rThG)(cdIw tzIy xDu, rThG)"
        );
    }

    #[test]
    fn basis_tests3() {
        assert_eq!(part_list(vec!["It's", "a", "very", "cool", "test"]),
                "(It's, a very cool test)(It's a, very cool test)(It's a very, cool test)(It's a very cool, test)");
    }

    #[test]
    fn basis_tests4() {
        assert_eq!(part_list(vec!["very", "short"]), "(very, short)");
    }

    #[test]
    fn basis_tests5() {
        assert_eq!(part_list(vec!["I", "really", "don't", "know", "what", "to", "write", "here"]),
                "(I, really don't know what to write here)(I really, don't know what to write here)(I really don't, know what to write here)(I really don't know, what to write here)(I really don't know what, to write here)(I really don't know what to, write here)(I really don't know what to write, here)");
    }
}
