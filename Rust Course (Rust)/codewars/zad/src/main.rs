use std::char::from_u32;

fn main() {
    count_by(0, 0);
    solution(0);
    move_zeros(&[0]);
    make_readable(0);
    snail(&[Vec::new()]);
    format_duration(132062240);
    rgb(1, 1, 1);
    println!("{}", rot13("dupsko"))
}

fn count_by(x: u32, n: u32) -> Vec<u32> {
    let mut res: Vec<u32> = Vec::new();

    for i in 1..=n {
        res.push(x * i);
    }
    return res;
}

fn solution(num: i32) -> i32 {
    if num < 0 {
        return 0;
    } else {
        return (3..num).filter(|i| i % 3 == 0 || i % 5 == 0).sum();
    }
}

fn move_zeros(arr: &[u8]) -> Vec<u8> {
    let mut res: Vec<u8> = Vec::new();
    let mut zeroes: Vec<u8> = Vec::new();

    for &elem in arr {
        if elem == 0u8 {
            zeroes.push(elem);
        } else {
            res.push(elem);
        }
    }
    res.append(&mut zeroes);
    return res;
}

fn make_readable(seconds: u32) -> String {
    let secs = seconds % 60;
    let mins = (seconds / 60) % 60;
    let hrs = seconds / 60 / 60;

    return format!("{:02}:{:02}:{:02}", hrs, mins, secs);
}

fn snail(matrix: &[Vec<i32>]) -> Vec<i32> {
    let mut res: Vec<i32> = Vec::new();
    let mut cur_dir = (0, 1);
    let mut cur_pos: (i32, i32) = (0, -1);

    if matrix == &[Vec::new()] {
        return res;
    }

    let it: Vec<i32> = (1..=matrix[0].len())
        .flat_map(|x| vec![x as i32, x as i32])
        .rev()
        .skip(1)
        .collect();
    for i in 0..it.len() {
        for _j in 1..=it[i] {
            cur_pos.0 += cur_dir.0;
            cur_pos.1 += cur_dir.1;
            res.push(matrix[cur_pos.0 as usize][cur_pos.1 as usize]);
        }

        cur_dir = match cur_dir {
            (0, 1) => (1, 0),
            (1, 0) => (0, -1),
            (0, -1) => (-1, 0),
            (-1, 0) => (0, 1),
            _ => panic!(),
        }
    }
    println! {"{:?}", res};
    return res;
}

fn format_duration(seconds: u64) -> String {
    if seconds == 0 {
        return "now".to_string();
    }

    let mut not_empty: Vec<String> = Vec::new();

    let mut secs_left = seconds;
    let years = secs_left / 31536000;

    secs_left -= years * 31536000;

    let days = secs_left / 86400;
    secs_left -= days * 86400;

    let hrs = secs_left / 3600;
    secs_left -= hrs * 3600;

    let mins = secs_left / 60;
    secs_left -= mins * 60;

    if years > 0 {
        not_empty.push(years.to_string() + if years > 1 { " years" } else { " year" })
    };
    if days > 0 {
        not_empty.push(days.to_string() + if days > 1 { " days" } else { " day" })
    };
    if hrs > 0 {
        not_empty.push(hrs.to_string() + if hrs > 1 { " hours" } else { " hour" })
    };
    if mins > 0 {
        not_empty.push(mins.to_string() + if mins > 1 { " minutes" } else { " minute" })
    };
    if secs_left > 0 {
        not_empty.push(secs_left.to_string() + if secs_left > 1 { " seconds" } else { " second" })
    };

    let mut res = not_empty[0].to_string();

    for i in 1..not_empty.len() {
        if i == not_empty.len() - 1 {
            res += " and ";
        } else {
            res += ", ";
        }
        res += &not_empty[i];
    }

    return res;
}

fn rgb(r: i32, g: i32, b: i32) -> String {
    let r_new = if r < 0 {
        0
    } else if r > 255 {
        255
    } else {
        r
    };

    let g_new = if g < 0 {
        0
    } else if g > 255 {
        255
    } else {
        g
    };

    let b_new = if b < 0 {
        0
    } else if b > 255 {
        255
    } else {
        b
    };
    return format!("{:02X}{:02X}{:02X}", r_new, g_new, b_new);
}

fn rot13(message: &str) -> String {
    message
        .chars()
        .map(|c| {
            if c.is_ascii_alphabetic() {
                let base = if c.is_ascii_uppercase() { b'A' } else { b'a' };
                ((c as u8 - base + 13) % 26 + base) as char
            } else {
                c
            }
        })
        .collect()
}
