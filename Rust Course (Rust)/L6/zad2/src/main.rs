fn main() {
}

mod solution {
    fn vec_to_string(cache: &Vec<i32>) -> String {
        match cache.len() {
            1 => cache[0].to_string(),
            2 => cache[0].to_string() + "," + &cache[1].to_string(),
            _ => cache[0].to_string() + "-" + &cache[cache.len() - 1].to_string(),
        }
    }

    pub fn range_extraction(a: &[i32]) -> String {
        let mut list: Vec<String> = vec![];
        let mut cache: Vec<i32> = vec![];
        for i in 0..a.len() {
            if cache.len() > 0 {
                if a[i] - 1 != *cache.last().unwrap() {
                    list.push(vec_to_string(&cache));
                    cache.clear();
                }
            }
            cache.push(a[i]);
            if i == a.len() - 1 {
                list.push(vec_to_string(&cache));
            }
        }
        list.join(",")
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example1() {
        assert_eq!(solution::range_extraction(&[-6,-3,-2,-1,0,1,3,4,5,7,8,9,10,11,14,15,17,18,19,20]), "-6,-3-1,3-5,7-11,14,15,17-20");	
    }

    #[test]
    fn example2() {
        assert_eq!(solution::range_extraction(&[-3,-2,-1,2,10,15,16,18,19,20]), "-3--1,2,10,15,16,18-20");
    }

    #[test]
    fn example3() {
        assert_eq!(solution::range_extraction(&[1,2,3,4,5,6,7,8,9,10]), "1-10");	
    }

    #[test]
    fn example4() {
        assert_eq!(solution::range_extraction(&[1,2,4,5,7,8,10,11]), "1,2,4,5,7,8,10,11");	
    }

    #[test]
    fn example5() {
        assert_eq!(solution::range_extraction(&[-10,-9,-8,-6,-5,-2,-1,0,1,3,4,6,8,11]), "-10--8,-6,-5,-2-1,3,4,6,8,11");
    }
}