use std::collections::HashSet;
fn main() {}

fn sum_pairs(ints: &[i8], s: i8) -> Option<(i8, i8)> {
    if ints.len() < 2 {
        return None;
    }

    let mut int_set = HashSet::new();
    int_set.insert(ints[0]);

    for i in 1..ints.len() {
        let needed = s - ints[i];
        if int_set.contains(&needed) {
            return Some((needed, ints[i]));
        }
        int_set.insert(ints[i]);
    }
    None
}
#[cfg(test)]
mod tests {
    use super::sum_pairs;

    #[test]
    fn returns_expected1() {
        assert_eq!(sum_pairs(&[1, 4, 8, 7, 3, 15], 8), Some((1, 7)));
    }

    #[test]
    fn returns_expected2() {
        assert_eq!(sum_pairs(&[1, -2, 3, 0, -6, 1], -6), Some((0, -6)));
    }

    #[test]
    fn returns_expected3() {
        assert_eq!(sum_pairs(&[20, -13, 40], -7), None);
    }

    #[test]
    fn returns_expected4() {
        assert_eq!(sum_pairs(&[1, 2, 3, 4, 1, 0], 2), Some((1, 1)));
    }

    #[test]
    fn returns_expected5() {
        assert_eq!(sum_pairs(&[10, 5, 2, 3, 7, 5], 10), Some((3, 7)));
    }
}
