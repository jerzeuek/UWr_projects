fn main() {
}

struct Cipher {
    map1: String,
    map2: String,
}

impl Cipher {
    fn new(map1: &str, map2: &str) -> Cipher {
        Cipher {
            map1: map1.to_string(),
            map2: map2.to_string(),
        }
    }

    fn encode(&self, string: &str) -> String {
        string
            .chars()
            .map(|c| {
                if let Some(idx) = self.map1.find(c) {
                    self.map2.chars().nth(idx).unwrap()
                } else {
                    c
                }
            })
            .collect()
    }

    fn decode(&self, string: &str) -> String {
        string
            .chars()
            .map(|c| {
                if let Some(idx) = self.map2.find(c) {
                    self.map1.chars().nth(idx).unwrap()
                } else {
                    c
                }
            })
            .collect()
    }
}

#[cfg(test)]
mod tests {
    use super::Cipher; 
    #[test]
    fn examples1() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "etaoinshrdlucmfwypvbgkjqxz";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("abc"), "eta");
    }

    #[test]
    fn examples2() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "etaoinshrdlucmfwypvbgkjqxz";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("xyz"), "qxz");
    }

    #[test]
    fn examples3() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "etaoinshrdlucmfwypvbgkjqxz";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.decode("eirfg"), "aeiou");
    }

    #[test]
    fn examples4() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "adsjakfljcyakflafjaofnefjt";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("gaivkzwcz"), "fajnytest");
    }

    #[test]
    fn examples5() {
        let map1 = "zzzzzzzzzzzzzzzzzzzzzzzzzz";
        let map2 = "etaoinshrdlucmfwypvbgkjqxz";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("lmao"), "lmao");
    }

    #[test]
    fn examples6() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "qwertyuiopasdfghjklzxcvbnm";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("verysecretmessage"), "ctknltektzdtllqut");
    }

    #[test]
    fn examples7() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "qwertyuiopasdfghjklzxcvbnm";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.decode("ctknltektzdtllqut"), "verysecretmessage");
    }

        #[test]
    fn examples8() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "qwertyuiopasdfghjklzxcvbnm";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.decode("qwertyuiopasdfghjklzxcvbnm"), "abcdefghijklmnopqrstuvwxyz");
    }

        #[test]
    fn examples9() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "qazwsxedcrfvtgbyhnujmikolp";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("rust is the best programming language"), "nmuj cu jds asuj ynbenqttcge vqgemqes");
    }
    
        #[test]
    fn examples10() {
        let map1 = "abcdefghijklmnopqrstuvwxyz";
        let map2 = "qazwsxedcrfvtgbyhnujmikolp";

        let cipher = Cipher::new(map1, map2);

        assert_eq!(cipher.encode("ą ę"), "ą ę");
    }

    }
