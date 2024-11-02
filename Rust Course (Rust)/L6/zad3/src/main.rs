fn main(){
}

fn hamming(n: usize) -> u64 {
    let mut nums = vec![1];
    let (mut i, mut j, mut k) = (0, 0, 0);

    while n > nums.len() {
        let next = *vec![2 * nums[i], 3 * nums[j], 5 * nums[k]]
            .iter()
            .min()
            .unwrap();
        
        nums.push(next);

        if next == 2 * nums[i] {
            i += 1;
        }
        if next == 3 * nums[j] {
            j += 1;
        }
        if next == 5 * nums[k] {
            k += 1;
        }
    }

    nums[n - 1]
}

#[cfg(test)]
mod tests {
    use super::hamming;

    #[test]
    fn sample_tests1() {
        assert_eq!(hamming(19), 32);
    }

    #[test]
    fn sample_tests2() {
        assert_eq!(hamming(42), 160);
    }

    #[test]
    fn sample_tests3() {
        assert_eq!(hamming(69), 540);
    }

    #[test]
    fn sample_tests4() {
        assert_eq!(hamming(420), 393216);
    }

    #[test]
    fn sample_tests5() {
        assert_eq!(hamming(2137), 13947137604);
    }
}
