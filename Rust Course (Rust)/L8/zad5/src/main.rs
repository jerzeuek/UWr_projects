fn main() {
    first_n_smallest(&[1, 2, 3, 4, 5], 0);
}

fn first_n_smallest(arr: &[i32], n: usize) -> Vec<i32> {
    let mut min: Vec<i32> = vec![];

    if n == 0 {
        return vec![];
    }

    if n == arr.len() {
        return arr.to_vec();
    }

    for i in 0..arr.len() {
        if min.len() < n {
            min.push(arr[i]);
        } else {
            let mut index = 0;
            for j in 1..n {
                if min[j] >= min[index] {
                    index = j;
                }
            }

            if arr[i] < min[index] {
                min.remove(index);
                min.push(arr[i]);
            }
        }
    }

    min
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_basic1() {
        assert_eq!(first_n_smallest(&[1, 2, 3, 1, 2], 3), [1, 2, 1]);
    }

    #[test]
    fn test_basic2() {
        assert_eq!(first_n_smallest(&[1, 2, 3, 4, 5], 0), []);
    }

    #[test]
    fn test_basic3() {
        assert_eq!(first_n_smallest(&[1, 2, 3, 4, 5], 5), [1, 2, 3, 4, 5]);
    }

    #[test]
    fn test_basic4() {
        assert_eq!(first_n_smallest(&[1, 2, 3, 4, 2], 4), [1, 2, 3, 2]);
    }

    #[test]
    fn test_basic5() {
        assert_eq!(first_n_smallest(&[2, 1, 2, 3, 4, 2], 2), [2, 1]);
    }
}
