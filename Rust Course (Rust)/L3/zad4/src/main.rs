fn main() {
}

fn zoom(n: i32) -> String {
    let characters: [char; 2];

    if n % 4 == 1 {
        characters = ['■', '□'];
    } else {
        characters = ['□', '■'];
    }

    let mut top_of_pat = String::new();

    for i in 0..(n / 2) {
        let mut left_of_row = String::new();
        let mut middle_of_row = String::new();

        for j in 0..i {
            left_of_row.push(characters[j as usize % 2]);
        }

        for _j in 0..n - (2 * i) {
            middle_of_row.push(characters[i as usize % 2]);
        }

        let right_of_row = left_of_row.chars().rev().collect::<String>();

        top_of_pat = top_of_pat + &left_of_row + &middle_of_row + &right_of_row;
        top_of_pat.push('\n');
    }

    let mut middle_of_pattern = String::new();

    for i in 0..n {
        middle_of_pattern.push(characters[i as usize % 2]);
    }

    let bottom_of_pattern = top_of_pat.chars().rev().collect::<String>();
    let whole = top_of_pat + &middle_of_pattern + &bottom_of_pattern;

    return whole;
}

#[cfg(test)]
mod tests {
    use super::zoom;

    #[test]
    fn basic_test_1() {
        assert_eq!(zoom(1), "■");
    }

    #[test]
    fn basic_test_2() {
        assert_eq!(
            zoom(3),
            "\
□□□
□■□
□□□"
        );
    }

    #[test]
    fn basic_test_3() {
        assert_eq!(
            zoom(5),
            "\
■■■■■
■□□□■
■□■□■
■□□□■
■■■■■"
        );
    }

    #[test]
    fn basic_test_4() {
        assert_eq!(
            zoom(7),
            "\
□□□□□□□
□■■■■■□
□■□□□■□
□■□■□■□
□■□□□■□
□■■■■■□
□□□□□□□"
        );
    }

    #[test]
    fn basic_test_5() {
        assert_eq!(
            zoom(9),
            "\
■■■■■■■■■
■□□□□□□□■
■□■■■■■□■
■□■□□□■□■
■□■□■□■□■
■□■□□□■□■
■□■■■■■□■
■□□□□□□□■
■■■■■■■■■"
        );
    }

    #[test]
    fn basic_test_6() {
        assert_eq!(
            zoom(11),
            "\
□□□□□□□□□□□
□■■■■■■■■■□
□■□□□□□□□■□
□■□■■■■■□■□
□■□■□□□■□■□
□■□■□■□■□■□
□■□■□□□■□■□
□■□■■■■■□■□
□■□□□□□□□■□
□■■■■■■■■■□
□□□□□□□□□□□"
        );
    }

    #[test]
    fn basic_test_7() {
        assert_eq!(
            zoom(13),
            "\
■■■■■■■■■■■■■
■□□□□□□□□□□□■
■□■■■■■■■■■□■
■□■□□□□□□□■□■
■□■□■■■■■□■□■
■□■□■□□□■□■□■
■□■□■□■□■□■□■
■□■□■□□□■□■□■
■□■□■■■■■□■□■
■□■□□□□□□□■□■
■□■■■■■■■■■□■
■□□□□□□□□□□□■
■■■■■■■■■■■■■"
        );
    }

    #[test]
    fn basic_test_8() {
        assert_eq!(
            zoom(15),
            "\
□□□□□□□□□□□□□□□
□■■■■■■■■■■■■■□
□■□□□□□□□□□□□■□
□■□■■■■■■■■■□■□
□■□■□□□□□□□■□■□
□■□■□■■■■■□■□■□
□■□■□■□□□■□■□■□
□■□■□■□■□■□■□■□
□■□■□■□□□■□■□■□
□■□■□■■■■■□■□■□
□■□■□□□□□□□■□■□
□■□■■■■■■■■■□■□
□■□□□□□□□□□□□■□
□■■■■■■■■■■■■■□
□□□□□□□□□□□□□□□"
        );
    }

    #[test]
    fn basic_test_9() {
        assert_eq!(
            zoom(17),
            "\
■■■■■■■■■■■■■■■■■
■□□□□□□□□□□□□□□□■
■□■■■■■■■■■■■■■□■
■□■□□□□□□□□□□□■□■
■□■□■■■■■■■■■□■□■
■□■□■□□□□□□□■□■□■
■□■□■□■■■■■□■□■□■
■□■□■□■□□□■□■□■□■
■□■□■□■□■□■□■□■□■
■□■□■□■□□□■□■□■□■
■□■□■□■■■■■□■□■□■
■□■□■□□□□□□□■□■□■
■□■□■■■■■■■■■□■□■
■□■□□□□□□□□□□□■□■
■□■■■■■■■■■■■■■□■
■□□□□□□□□□□□□□□□■
■■■■■■■■■■■■■■■■■"
        );
    }

    #[test]
    fn basic_test_10() {
        assert_eq!(
            zoom(19),
            "\
□□□□□□□□□□□□□□□□□□□
□■■■■■■■■■■■■■■■■■□
□■□□□□□□□□□□□□□□□■□
□■□■■■■■■■■■■■■■□■□
□■□■□□□□□□□□□□□■□■□
□■□■□■■■■■■■■■□■□■□
□■□■□■□□□□□□□■□■□■□
□■□■□■□■■■■■□■□■□■□
□■□■□■□■□□□■□■□■□■□
□■□■□■□■□■□■□■□■□■□
□■□■□■□■□□□■□■□■□■□
□■□■□■□■■■■■□■□■□■□
□■□■□■□□□□□□□■□■□■□
□■□■□■■■■■■■■■□■□■□
□■□■□□□□□□□□□□□■□■□
□■□■■■■■■■■■■■■■□■□
□■□□□□□□□□□□□□□□□■□
□■■■■■■■■■■■■■■■■■□
□□□□□□□□□□□□□□□□□□□"
        );
    }
}
