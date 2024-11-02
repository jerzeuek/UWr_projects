fn main() {
}

fn comp(a: Vec<i64>, b: Vec<i64>) -> bool {
    let mut a_squared_sorted: Vec<i64> = a.into_iter()
                                   .map(|i| i.pow(2))
                                   .collect();
    a_squared_sorted.sort();
    let mut b_sorted: Vec<i64> = b;
    b_sorted.sort();

    return a_squared_sorted == b_sorted;
}

#[cfg(test)]
    mod tests {
    use super::comp;

    #[test]
    fn tests_comp1() {
        let a1 = vec![121, 144, 19, 161, 19, 144, 19, 11];
        let a2 = vec![11*11, 121*121, 144*144, 19*19, 161*161, 19*19, 144*144, 19*19];
        assert_eq!(comp(a1, a2), true);
    }

    #[test]
    fn tests_comp2() {
        let a1 = vec![121, 144, 19, 161, 19, 144, 19, 11];
        let a2 = vec![11*21, 121*121, 144*144, 19*19, 161*161, 19*19, 144*144, 19*19];
        assert_eq!(comp(a1, a2), false);
    }

    #[test]
    fn tests_comp3() {
        let a1 = vec![121, 144, 19, 161, 19, 144, 19, 11];
        let a2 = vec![11*11, 121*121, 144*144, 19*18, 161*161, 19*19, 144*144, 19*19];
        assert_eq!(comp(a1, a2), false);
    }

    #[test]
    fn tests_comp4() {
        let a1 = vec![121, 144, 19, 161, 19, 144, 19, 11];
        let a2 = vec![11*11, 121*121, 144*144, 19*19, 151*161, 19*19, 144*144, 19*19];
        assert_eq!(comp(a1, a2), false);
    }

    #[test]
    fn tests_comp5() {
        let a1 = vec![121, 144, 19, 161, 19, 144, 19, 11];
        let a2 = vec![11*11, 121*121, 144*142, 19*19, 161*161, 19*19, 144*144, 19*19];
        assert_eq!(comp(a1, a2), false);
    }
}