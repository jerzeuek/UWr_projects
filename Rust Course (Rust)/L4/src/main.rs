fn main()
{
    create_mandelbrot(4000, 4000, -2.0, 2.0, -2.0, 2.0, 1000, "fraktal1.ppm");
    create_mandelbrot(2880, 2160, -0.27, -0.20, -0.74, -0.71, 1000, "fraktal2.ppm");
    create_mandelbrot(4000, 2000, -0.60, -0.49, -0.66, -0.62, 1000, "fraktal3.ppm");
    create_mandelbrot(3840, 2160, -0.55, 0.33, -0.76, -0.43, 1000, "fraktal4.ppm");
}

pub struct Image
{
    pub size: (u32,u32),
    red: Vec<Vec<u8>>,
    green: Vec<Vec<u8>>,
    blue: Vec<Vec<u8>>
}

impl Image
{
    pub fn new(size: (u32,u32)) -> Self
    {
        Self
        {
            size,
            red: vec![vec![0;size.1 as usize];size.0 as usize],
            green: vec![vec![0;size.1 as usize];size.0 as usize],
            blue: vec![vec![0;size.1 as usize];size.0 as usize]
        }
    }

    pub fn get_pixel(&self, pixel: (u32,u32)) -> (u8,u8,u8)
    {
        let x: usize = pixel.0 as usize;
        let y: usize = pixel.1 as usize;
        (self.red[x][y],self.green[x][y],self.blue[x][y])
    }

    pub fn set_pixel(&mut self, pixel: (u32,u32), color: (u8,u8,u8))
    {
        let x: usize = pixel.0 as usize;
        let y: usize = pixel.1 as usize;
        self.red[x][y] = color.0;
        self.green[x][y] = color.1;
        self.blue[x][y] = color.2;
    }

    pub fn to_ppm(&self) -> String
    {
        let header = "P3\n".to_string() + &self.size.0.to_string() + " " + &self.size.1.to_string() + "\n255\n";
        let mut body = "".to_string();
        for j in 0..self.size.1 as usize
        {
            for i in 0..self.size.0 as usize
            {
                body = body + &self.red[i][j].to_string() + " ";
                body = body + &self.green[i][j].to_string() + " ";
                body = body + &self.blue[i][j].to_string() + " ";
            }
        }
        header+&body
    }
    pub fn save_ppm(&self, filename: &str)
    {
        use std::fs::File;
        use std::io::prelude::*;
        use std::path::Path;

        let path = Path::new(filename);
        let display = path.display();
        let mut file = match File::create(&path)
        {
            Err(why) => panic!("couldn't create {}: {}", display, why),
            Ok(file) => file,
        };
        match file.write_all(self.to_ppm().as_bytes())
        {
            Err(why) => panic!("couldn't write to {}: {}", display, why),
            Ok(_) => println!("successfully wrote to {}", display),
        }
    }

}

#[derive(Copy,Clone)]
pub struct Complex
{
    pub real: f64,
    pub imaginary: f64
}

impl Complex
{
    pub fn new() -> Self
    {
        Self
        {
            real: 0.0,
            imaginary: 0.0
        }
    }
    pub fn from(real: f64) -> Self
    {
        Self
        {
            real,
            imaginary: 0.0
        }
    }
    pub fn modulo(&self) -> f64
    {
        (self.real*self.real+self.imaginary*self.imaginary).sqrt()
    }
}

use std::ops;

impl ops::Add<Complex> for Complex {
    type Output = Complex;

    fn add(self, rhs: Complex) -> Complex {
        let mut temp = Complex::new();
        temp.real=self.real+rhs.real;
        temp.imaginary=self.imaginary+rhs.imaginary;
        temp
    }
}
impl ops::Sub<Complex> for Complex {
    type Output = Complex;

    fn sub(self, rhs: Complex) -> Complex {
        let mut temp = Complex::new();
        temp.real=self.real-rhs.real;
        temp.imaginary=self.imaginary-rhs.imaginary;
        temp
    }
}
impl ops::Mul<Complex> for Complex {
    type Output = Complex;

    fn mul(self, rhs: Complex) -> Complex {
        let mut temp = Complex::new();
        let a=self.real;
        let b=self.imaginary;
        let c=rhs.real;
        let d=rhs.imaginary;
        temp.real=a*c-b*d;
        temp.imaginary=b*c+a*d;
        temp
    }
}
impl ops::Div<Complex> for Complex {
    type Output = Complex;
    
    fn div(self, rhs: Complex) -> Complex {
        let mut temp = Complex::new();
        let a=self.real;
        let b=self.imaginary;
        let c=rhs.real;
        let d=rhs.imaginary;
        temp.real=(a*c+b*d)/(c*c+d*d);
        temp.imaginary=(b*c-a*d)/(c*c+d*d);
        temp
    }
}


fn create_mandelbrot(res_x: u32, res_y: u32, x_start: f64, x_end: f64, y_start: f64, y_end: f64, iter: i32, name: &str)
{
    //resolution
    let res_fx:f64 = res_x.into();
    let res_fy:f64 = res_y.into();
    let mut a = Image::new((res_x,res_y));

    //numbers
    let mut c = Complex::new();
    let mut z = Complex::new();
    let mut counter: i32;
    let mut fi: f64;
    let mut fj: f64;
    for i in 0..res_x
    {
        for j in 0..res_y
        {
            fi=i.into();
            fj=j.into();
            c.real=x_start + fi/res_fx * (x_end - x_start);
            c.imaginary=y_start + fj/res_fy * (y_end - y_start);
            z.real=0.0;
            z.imaginary=0.0;
            counter=0;
            while z.modulo() <= 2.0 && counter < iter
            {
                z = z*z + c;
                counter+=1;
            }
            if counter<iter
            {
                a.set_pixel((i,j), (counter as u8,0,0));
            }
            else
            {
                a.set_pixel((i,j), (0,0,(
                    z.modulo()*128.0) as u8));
            }
        }
    }
    a.save_ppm(&name);

}