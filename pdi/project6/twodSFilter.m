function g = twodSFilter(f, w)
% twodSFilter  2-D spatial linear filtering with replicate padding.
%
% Syntax:
%   g = twodSFilter(f, w)
%
% Inputs:
%   f  - Input image (M x N grayscale or M x N x C colour).
%        Values may be uint8, uint16, double, etc.
%   w  - Filter kernel (Kh x Kw matrix). Must have odd dimensions.
%        The kernel is automatically normalised so its coefficients sum to 1.
%
% Output:
%   g  - Filtered image, double, scaled to [0,1].
%
% Behaviour:
%   * The input is first scaled to [0,1] (double).
%   * Replicate padding of floor(Kh/2) rows and floor(Kw/2) columns is
%     applied before filtering so the output has the same size as the input.
%   * The filtering operation is 2-D spatial correlation:
%       g(x,y) = sum_s sum_t w_norm(s,t) * f_padded(x+s, y+t)
%     which is implemented via conv2 with a 180-rotated kernel.
%
% Example - averaging (box) filter:
%   f = imread('Fig3.37(a).jpg');
%   w3  = ones(3,3);
%   g3  = twodSFilter(f, w3);   % 3x3 box filter
%   w11 = ones(11,11);
%   g11 = twodSFilter(f, w11);  % 11x11 box filter
%
% Dependencies:
%   imagePad4e  (Project #4)
%
% Author: Pedro Victor dos Santos Oliveira
% Disciplina: Processamento Digital de Imagens - UFAM (2026)

    % ----- argument validation -----------------------------------------------
    if nargin < 2
        error('twodSFilter: two arguments (f, w) are required.');
    end
    if ~isnumeric(w) || ndims(w) ~= 2 %#ok<ISMAT>
        error('twodSFilter: kernel w must be a 2-D numeric matrix.');
    end
    if sum(w(:)) == 0
        error('twodSFilter: kernel w must have non-zero sum for normalisation.');
    end

    % ----- scale input to [0,1] (double) ------------------------------------
    f_d = double(f);
    f_max = max(f_d(:));
    if f_max > 0
        f_d = f_d / f_max;
    end

    % ----- handle colour images channel by channel --------------------------
    [~, ~, nch] = size(f_d);
    if nch > 1
        g = zeros(size(f_d));
        for ch = 1:nch
            g(:,:,ch) = twodSFilter(f_d(:,:,ch), w);
        end
        return;
    end

    % ----- padding parameters -----------------------------------------------
    [Kh, Kw] = size(w);
    r = floor(Kh / 2);   % rows of padding (top & bottom)
    c = floor(Kw / 2);   % columns of padding (left & right)

    % ----- replicate padding ------------------------------------------------
    fp = imagePad4e(f_d, r, c, 'replicate');

    % ----- normalise kernel (sum = 1) ----------------------------------------
    w_norm = w / sum(w(:));

    % ----- 2-D correlation via conv2 with flipped kernel --------------------
    % conv2(A, B, 'valid') computes convolution. To obtain correlation, the
    % kernel is rotated 180deg: correlation(f, w) = conv(f, rot180(w)).
    g = conv2(fp, rot90(w_norm, 2), 'valid');

    % Clip to [0,1] to handle any floating-point overshoot
    g = max(0, min(1, g));

end
