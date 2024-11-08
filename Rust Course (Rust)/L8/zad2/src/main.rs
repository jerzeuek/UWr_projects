fn main(){
    println!("{}", likes(&[]))
}

fn likes(names: &[&str]) -> String {
    match names.len(){
        0 => "no one likes this".to_string(),
        1 => format!("{} likes this", names[0]),
        2 => format!("{} and {} like this", names[0], names[1]),
        3 => format!{"{}, {} and {} like this", names[0], names[1], names[2]},
        l => format!{"{}, {} and {} others like this", names[0], names[1], l-2}
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example_tests1() {
        assert_eq!(likes(&[]), "no one likes this");
    }

    #[test]
    fn example_tests2() {
        assert_eq!(likes(&["Peter"]), "Peter likes this");
    }

    #[test]
    fn example_tests3() {
        assert_eq!(likes(&["Jacob", "Alex"]), "Jacob and Alex like this");
    }

    #[test]
    fn example_tests4() {
        assert_eq!(
            likes(&["Max", "John", "Mark"]),
            "Max, John and Mark like this"
        );
    }

    #[test]
    fn example_tests() {
        assert_eq!(
            likes(&["Alex", "Jacob", "Mark", "Max"]),
            "Alex, Jacob and 2 others like this"
        );
    }
}