fn main() {
}

fn capitalize(s: &str) -> Vec<String> {
    vec![s.chars()
          .enumerate()
          .map(|(i, c)| if i%2==0 
                        {c.to_ascii_uppercase()} 
                        else
                        {c})
          .collect(),
         
         s.chars()
          .enumerate()
          .map(|(i, c)| if i%2==1 
                        {c.to_ascii_uppercase()} 
                        else
                        {c})
          .collect()]
}

#[cfg(test)]
mod tests {
    use super::capitalize;

    #[test]
    fn example_tests1() {
        assert_eq!(capitalize("abcdef"),["AbCdEf", "aBcDeF"]);
    }

    #[test]
    fn example_tests2() {
        assert_eq!(capitalize("codewars"),["CoDeWaRs", "cOdEwArS"]);
    }

    #[test]
    fn example_tests3() {
        assert_eq!(capitalize("abracadabra"),["AbRaCaDaBrA", "aBrAcAdAbRa"]);
    }

    #[test]
    fn example_tests4() {
        assert_eq!(capitalize("codewarriors"),["CoDeWaRrIoRs", "cOdEwArRiOrS"]);
    }

    #[test]
    fn example_tests5() {
        assert_eq!(capitalize("whyareyoutestingme"),["WhYaReYoUtEsTiNgMe", "wHyArEyOuTeStInGmE"]);
    }
}