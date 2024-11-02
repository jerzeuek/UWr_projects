fn main() {
}


fn even_numbers(array: &Vec<i32>, number: usize) -> Vec<i32> {
    array.into_iter()
         .filter(|&&n| n%2==0)
         .rev()
         .take(number)        
         .cloned()
         .collect::<Vec<i32>>()
         .into_iter()
         .rev()
         .collect()
}

#[cfg(test)]
mod tests {
    use super::even_numbers;
    
    #[test]
    fn sample_tests1() {
        assert_eq!(even_numbers(&vec!(1, 2, 3, 4, 5, 6, 7, 8, 9), 3), vec!(4, 6, 8));
    }
    
    #[test]
    fn sample_tests2() {
        assert_eq!(even_numbers(&vec!(-22, 5, 3, 11, 26, -6, -7, -8, -9, -8, 26), 2), vec!(-8, 26));
    } 
    
    #[test]
    fn sample_tests3() {
        assert_eq!(even_numbers(&vec!(6, -25, 3, 7, 5, 5, 7, -3, 23), 1), vec!(6));
    } 
    
    #[test]
    fn sample_tests4() {
        assert_eq!(even_numbers(&vec!(2, 1, 3, 7, 4, 20, 6, 9, 42), 2), vec!(6, 42));
    } 
    
    #[test]
    fn sample_tests5() {
        assert_eq!(even_numbers(&vec!(2, 4, 6, 8, 10), 0), vec!());
    } 
}