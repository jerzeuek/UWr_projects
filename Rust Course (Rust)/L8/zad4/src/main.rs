fn main() {
}

fn create_tables(n: i32) -> (Vec<i32>, Vec<i32>){
    let mut john_vec = vec![0];
    let mut ann_vec = vec![1];

    for i in 1..n{
        john_vec.push(i - ann_vec[john_vec[i as usize - 1] as usize]);
        ann_vec.push(i - john_vec[ann_vec[i as usize - 1] as usize])
    }

    (john_vec, ann_vec)
} 

fn john(n: i32) -> Vec<i32> {
    create_tables(n).0
}

fn ann(n: i32) -> Vec<i32> {
    create_tables(n).1
}

fn sum_john(n: i32) -> i32 {
    create_tables(n).0.iter().sum()
}

fn sum_ann(n: i32) -> i32 {
    create_tables(n).1.iter().sum()
} 

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_john() {
        assert_eq!(john(14), vec![0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 7, 8]);
    }
    #[test]
    fn test_ann() {
        assert_eq!(ann(15), vec![1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6, 7, 8, 8, 9]);
    }
    #[test]
    fn test_sum_john() {
        assert_eq!(sum_john(78), 1861);
    }
    #[test]
    fn test_sum_ann() {
        assert_eq!(sum_ann(150), 6930);
    }

    #[test]
    fn test_both(){
        assert_eq!(create_tables(20), (vec![0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 7, 8, 9, 9, 10, 11, 11, 12], 
                                       vec![1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6, 7, 8, 8, 9, 9, 10, 11, 11, 12]));
    }
}